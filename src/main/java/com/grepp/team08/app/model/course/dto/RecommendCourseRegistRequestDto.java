package com.grepp.team08.app.model.course.dto;

import java.util.List;
import lombok.Data;

@Data
public class RecommendCourseRegistRequestDto {
    private Long courseId;
    private List<String> imageUrls;
}
