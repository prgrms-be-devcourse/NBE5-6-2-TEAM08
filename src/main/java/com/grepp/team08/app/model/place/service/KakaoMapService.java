package com.grepp.team08.app.model.place.service;

import com.grepp.team08.app.model.place.dto.KakaoMapDto;
import com.grepp.team08.app.model.place.entity.KakaoMap;
import com.grepp.team08.app.model.place.repository.KakaoMapRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KakaoMapService {

    private final KakaoMapRepository kakaoMapRepository;
    public KakaoMapService(KakaoMapRepository kakaoMapRepository) {
        this.kakaoMapRepository = kakaoMapRepository;
    }

    public List<KakaoMapDto> getAllPlaceByKeyword(String keyword) {
        List<KakaoMap> places = kakaoMapRepository.getPlaceInfoByKeyword(keyword);
        // 엔티티를 DTO로 변환
        return places.stream()
            .map(place -> new KakaoMapDto(place))
            .collect(Collectors.toList());
    }



}
