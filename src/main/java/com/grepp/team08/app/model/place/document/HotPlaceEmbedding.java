package com.grepp.team08.app.model.place.document;

import com.grepp.team08.app.model.recommend.code.Category;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import jakarta.persistence.Id;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "places")
public class HotPlaceEmbedding {

    @Id
    private String id;
    private String text; // 전체 정보 텍스트 임베딩 (기존)
    private float[] embedding; // 전체 정보 텍스트 임베딩 벡터 (기존)

    private String placeName;
    private Category category;
    private String address;
    private String latitude;
    private String longitude;
    private List<String> keywords;
    // keywords 임베딩을 위한 setter 메서드 (DataInitializeService에서 사용)
    @Setter
    private float[] keywordsEmbedding; // keywords 임베딩 벡터를 저장할 필드 추가

    // 전체 정보 임베딩 생성자 (기존)
    public HotPlaceEmbedding(String id, String placeName, String category, String address, String latitude, String longitude, List<String> keywords, String text, float[] embedding) {
        this.id = id;
        this.placeName = placeName;
        this.category = Category.valueOf(category.toUpperCase());
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.keywords = keywords;
        this.text = text;
        this.embedding = embedding;
    }

    // TextSegment와 Embedding을 받아 HotPlaceEmbedding 객체를 생성하는 생성자 (기존)
    public HotPlaceEmbedding(TextSegment segment, Embedding embedding) {
        this.text = segment.text();
        this.embedding = embedding.vector();
        dev.langchain4j.data.document.Metadata metadata = segment.metadata();
        if (metadata != null) {
            this.placeName = metadata.getString("placeName");
            String keywordsString = metadata.getString("keywords");
            if (keywordsString != null) {
                this.keywords = Arrays.asList(keywordsString.split(","));
            }
            // 필요한 경우 다른 메타데이터도 여기서 가져올 수 있습니다.
        }
    }

    public void embed(EmbeddingModel model) {
        TextSegment segment = TextSegment.from(this.toString());
        this.text = segment.text();
        Embedding embedding = model.embed(segment).content();
        this.embedding = embedding.vector();
    }

    @Override
    public String toString() {
        return "HotPlaceEmbedding{" +
            "id='" + id + '\'' +
            ", placeName='" + placeName + '\'' +
            ", category=" + category +
            ", address='" + address + '\'' +
            ", latitude='" + latitude + '\'' +
            ", longitude='" + longitude + '\'' +
            ", keywords=" + keywords +
            '}';
    }
}
