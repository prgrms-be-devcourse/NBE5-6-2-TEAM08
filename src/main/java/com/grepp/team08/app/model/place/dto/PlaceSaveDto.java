package com.grepp.team08.app.model.place.dto;

public record PlaceSaveDto(
    String placeName,
    String address,
    double latitude,
    double longitude
) {

}
