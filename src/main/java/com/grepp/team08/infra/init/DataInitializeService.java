package com.grepp.team08.infra.init;

import com.grepp.team08.app.model.place.entity.HotPlace;
import com.grepp.team08.app.model.place.repository.HotPlaceRepository;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DataInitializeService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final HotPlaceRepository hotPlaceRepository;

    @Transactional
    public void initializeHotPlaceVector() {
        if (hotPlaceRepository.count() > 0) {
            System.out.println("이미 HotPlace 데이터가 존재합니다. 벡터 임베딩을 초기화합니다.");
        }

        List<HotPlace> hotPlaces = hotPlaceRepository.findAll();
        System.out.println("Found " + hotPlaces.size() + " HotPlaces to embed.");

        for (HotPlace hotPlace : hotPlaces) {
            String textToEmbed = hotPlace.getPlaceName() + " " + hotPlace.getCategory();
            TextSegment segment = TextSegment.from(textToEmbed, new Metadata(Map.of("placeId", hotPlace.getPlaceId().toString())));
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
            System.out.println("Embedded and stored: " + hotPlace.getPlaceName() + " (" + hotPlace.getCategory() + ")");
        }

        System.out.println("HotPlace 벡터 임베딩 초기화 완료.");
    }
}
