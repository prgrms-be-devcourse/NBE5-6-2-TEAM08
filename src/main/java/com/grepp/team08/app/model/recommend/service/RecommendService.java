package com.grepp.team08.app.model.recommend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepp.team08.app.model.place.dto.PlaceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendAiService recommendAiService;
    private final ObjectMapper objectMapper;

    public List<PlaceDto> recommendPlaces(String moodText) {
        String response = recommendAiService.recommend(moodText).text();
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
//            log.warn("Gemini 응답 JSON 파싱 실패: {}", response);
            throw new RuntimeException("추천 결과 파싱 실패", e);
        }
    }
}
