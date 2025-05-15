package com.grepp.team08.app.model.recommend.dto;

import com.grepp.team08.app.model.recommend.code.Mood;
import java.util.List;

public record RecommendDto(
    double lat,
    double lon,
    List<Mood> moods
) {}
