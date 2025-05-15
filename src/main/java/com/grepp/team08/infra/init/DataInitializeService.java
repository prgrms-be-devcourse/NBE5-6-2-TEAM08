package com.grepp.team08.infra.init;

import com.grepp.team08.app.model.place.document.HotPlaceEmbedding;
import com.grepp.team08.app.model.place.repository.HotPlaceEmbeddingRepository;
import com.grepp.team08.app.model.recommend.code.Category;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DataInitializeService {

    private final EmbeddingModel embeddingModel;
    private final HotPlaceEmbeddingRepository hotPlaceEmbeddingRepository;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public void initializeVector() {
        try {
            String HOT_PLACE_JSON_PATH = "src/main/resources/hot_places.json";
            List<HotPlaceJson> hotPlaceJsons = JsonReader.readHotPlacesFromJson(HOT_PLACE_JSON_PATH);
            if (hotPlaceJsons == null || hotPlaceJsons.isEmpty()) {
                System.out.println("JSON 파일이 비어 있거나 읽을 수 없습니다.");
                return;
            }

            ArrayList<Document> docs = new ArrayList<>();
            List<HotPlaceEmbedding> hotPlaceEmbeddings = new ArrayList<>();

            for (HotPlaceJson json : hotPlaceJsons) {
                String id = String.valueOf(json.getPlace_id());
                String placeName = json.getPlace_name();
                // 슬래시를 언더바로 바꿔줘야 함.
                String rawCategory = json.getCategory(); // 예: "카페/디저트"
                String enumFormatted = rawCategory.replace("/", "_"); // 예: "카페_디저트"

                String address = json.getAddress();
                String latitude = String.valueOf(json.getLatitude());
                String longitude = String.valueOf(json.getLongitude());
                List<String> keywords = json.getKeywords();

                // LangChain4j 의 Metadata는 key-value 쌍에서 value로 List나 ArrayList를 지원하지 않으므로,
                // 문자열로 받아야 한다.
                String keywordText = String.join(", ", keywords);


                String text = """
                장소 이름: %s
                주소 : %s
                분위기 키워드: %s
                """.formatted(placeName, address, keywordText);

                TextSegment segment = TextSegment.from(
                    text,
                    new Metadata(Map.of(
                        "placeName", placeName,
                        "keywords", keywordText
                    ))
                );
                Embedding embedding = embeddingModel.embed(segment).content();



                HotPlaceEmbedding embeddingEntity = new HotPlaceEmbedding(
                    id, placeName, enumFormatted, address, latitude, longitude, keywords, segment.text(), embedding.vector()
                );

                // keywords만 따로 임베딩 (선택적)
                if (!keywords.isEmpty()) {
                    TextSegment keywordSegment = TextSegment.from(keywordText);
                    Embedding keywordsEmbedding = embeddingModel.embed(keywordSegment).content();
                    embeddingEntity.setKeywordsEmbedding(keywordsEmbedding.vector());
                }

                hotPlaceEmbeddings.add(embeddingEntity);
            }

            // HotPlaceEmbedding 객체들을 MongoDB에 저장
            hotPlaceEmbeddingRepository.saveAll(hotPlaceEmbeddings);
            System.out.println("HotPlace embeddings (including keywords) saved to MongoDB.");

        } catch (IOException e) {
            System.err.println("JSON 파일 처리 오류: " + e.getMessage());
        }
    }
}
