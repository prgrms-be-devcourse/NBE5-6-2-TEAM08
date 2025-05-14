package com.grepp.team08.app.model.recommend.service;

import com.grepp.team08.app.model.place.dto.PlaceDto;
import com.grepp.team08.app.model.recommend.dto.RecommendDto;
import java.util.List;

public interface RecommendService {

    List<PlaceDto> recommendDateCourse(RecommendDto dto);




}
