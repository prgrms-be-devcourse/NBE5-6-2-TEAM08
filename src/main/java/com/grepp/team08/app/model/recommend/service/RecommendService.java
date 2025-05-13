package com.grepp.team08.app.model.recommend.service;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.place.dto.PlaceDto;
import com.grepp.team08.app.model.recommend.dto.RecommendDto;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import java.util.List;

@AiService(
    wiringMode = AiServiceWiringMode.EXPLICIT,
    chatModel = "googleAiGeminiChatModel"//, contentRetriever = "embeddingStoreContentRetriever"
)
public interface RecommendService {

    @SystemMessage("""
        너는 대한민국 데이트 코스 큐레이터야.
        관심 카테고리와 강남구 동 정보를 보고
        장소 5곳을 JSON 배열 한 줄로만 추천한다.
        형식: [ { "name":"", "address":"" } ×5 ]
        """)

    @UserMessage("""
        - 관심 카테고리: #{ request.categories.?[true].![name()].join(', ') }
        - 강남구 동: #{ request.dong }
        """)

    List<PlaceDto> recommendDateCourse(RecommendDto dto);




}
