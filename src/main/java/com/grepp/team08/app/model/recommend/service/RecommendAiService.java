package com.grepp.team08.app.model.recommend.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
    wiringMode   = AiServiceWiringMode.EXPLICIT,
    chatModel    = "googleAiGeminiChatModel",
    contentRetriever = "embeddingStoreContentRetriever"
)
public interface RecommendAiService {

    @SystemMessage("""
        너는 대한민국 최고의 데이트 코스 큐레이터야.
        응답은 **순수 JSON 배열 한 줄**로만 반환해.  
        각 요소는 name, address, description 3개의 속성을 가져야 해.  
        예시:
        [
          {"name":"장소1","address":"주소1","description":"분위기 좋은 카페"}, 
          {"name":"장소2","address":"주소2","description":"야경이 예쁜 루프탑 바"},
          …
        ]
        """)
    String callGemini(@UserMessage String prompt);
}
