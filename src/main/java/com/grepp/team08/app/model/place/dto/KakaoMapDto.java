package com.grepp.team08.app.model.place.dto;

import com.grepp.team08.app.model.place.entity.KakaoMap;
import lombok.Data;

@Data
public class KakaoMapDto {
    private String addressName;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String categoryName;
    private String distance;
    private String id;
    private String phone;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String x;
    private String y;

    public KakaoMapDto(KakaoMap kakaoMap) {
        this.addressName = kakaoMap.getAddressName();
        this.categoryGroupCode = kakaoMap.getCategoryGroupCode();
        this.categoryGroupName = kakaoMap.getCategoryGroupName();
        this.categoryName = kakaoMap.getCategoryName();
        this.distance = kakaoMap.getDistance();
        this.id = kakaoMap.getId();
        this.phone = kakaoMap.getPhone();
        this.placeName = kakaoMap.getPlaceName();
        this.placeUrl = kakaoMap.getPlaceUrl();
        this.roadAddressName = kakaoMap.getRoadAddressName();
        this.x = kakaoMap.getX();
        this.y = kakaoMap.getY();
    }
}
