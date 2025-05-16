package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import java.util.List;

public record EditorCourseSaveDto(
    String title,
    String description,
    List<PlaceSaveDto> places,
    List<String> imageUrls
) {



}
