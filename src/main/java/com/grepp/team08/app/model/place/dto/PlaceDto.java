package com.grepp.team08.app.model.place.dto;

import java.util.List;

public record PlaceDto(
    String placeName,
    String address,
    String reason
) {}