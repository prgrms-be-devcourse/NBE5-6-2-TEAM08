package com.grepp.team08.app.model.recommend.service;

import dev.langchain4j.data.message.AiMessage;
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
    당신은 사용자에게 데이트 장소를 추천해주는 AI입니다.
    아래 문맥(context)은 장소들의 정보입니다.
    
    사용자의 분위기 요청에 따라 장소 5개를 추천해주세요.
    추천할 때는 식당 외에도 **카페, 공원, 전시관, 독특한 공간** 등 **다양한 유형의 장소**를 고려해주세요.
    
    응답은 아래의 JSON 형식을 따르세요:
    
    [
      {
        "placeName": "장소 이름",
        "address": "주소",
        "reason": "추천 이유"
      }
    ]
    """)

    AiMessage recommend(String userPrompt);
}
