package com.grepp.team08.app.model.recommend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepp.team08.app.model.place.dto.PlaceDto;
import com.grepp.team08.app.model.recommend.dto.RecommendDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final RecommendAiService aiService;
    private final ObjectMapper objectMapper;

    @Override
    public List<PlaceDto> recommendDateCourse(RecommendDto dto) {
        String cats   = String.join(", ", dto.categories().stream().map(Enum::name).toList());
        String region = dto.dong().name();

        String prompt = String.format(
            "사용자가 선택한 카테고리: %s\n" +
                "사용자가 선택한 지역(동): %s\n" +
                "각 카테고리마다 한 개씩, 총 %d개의 장소를 추천해줘.\n" +
                "응답은 순수 JSON 배열 한 줄, 각 요소에 category,name,address,description 필드를 포함해줘.",
            cats, region, dto.categories().size()
        );

        String raw = aiService.callGemini(prompt);

        // 코드펜스·백틱 제거
        String json = raw
            .strip()
            .replaceAll("^```json\\s*", "")
            .replaceAll("^```\\s*", "")
            .replaceAll("```$", "")
            .replaceAll("^`", "")
            .replaceAll("`$", "");

        try {
            return objectMapper.readValue(
                json,
                new TypeReference<List<PlaceDto>>() {}
            );
        } catch (Exception e) {
            throw new IllegalStateException("Gemini 응답 파싱 실패: [" + json + "]", e);
        }
    }
}
