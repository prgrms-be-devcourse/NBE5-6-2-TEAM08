package com.grepp.team08.app.model.recommend.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.STRING)
public enum Category {
    음식점, 카페, 힐링, 자연,
    문화시설, 실내, 야경, 드라이브,
    숙박, 관광명소
}
