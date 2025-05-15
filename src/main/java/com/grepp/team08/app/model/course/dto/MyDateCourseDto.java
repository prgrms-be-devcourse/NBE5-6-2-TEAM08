package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import java.util.List;
import lombok.Data;

public record MyDateCourseDto(
    String title,
    String description,
    String date,
    List<PlaceSaveDto> places
) {


}
