package com.grepp.team08.app.controller.api.place;

import com.grepp.team08.app.model.place.dto.KakaoMapDto;
import com.grepp.team08.app.model.place.service.KakaoMapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("https://dapi.kakao.com/v2")
public class KakaoMapApiController {

    private final KakaoMapService kakaoMapService;

    public KakaoMapApiController(KakaoMapService kakaoMapService) {
        this.kakaoMapService = kakaoMapService;
    }

    @GetMapping("/local/search/")
    public List<KakaoMapDto> getAllPlaceByKeyword(@RequestParam String keyword) {
        return kakaoMapService.getAllPlaceByKeyword(keyword);
    }

}
