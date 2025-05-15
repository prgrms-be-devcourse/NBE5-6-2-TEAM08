package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.member.entity.Member;
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

    public Course toEntity() {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setId(id);
        return course;
    }

    @Data
    public static class PlaceDto {
        private String name;
        private String address;
        private Integer rank;
    }
}