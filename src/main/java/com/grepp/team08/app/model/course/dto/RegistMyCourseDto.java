package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.place.dto.PlaceDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class RegistMyCourseDto {
    private Long courseId;
    private Boolean activated;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String description;
    private String title;
    private Member id;
    private List<PlaceDto> places;

}