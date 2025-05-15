package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RegistCourseDto {
    private Long courseId;
    private Boolean activated;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String description;
    private String title;
    private Member id;

    public Course toEntity(){
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setId(id);
        return course;
    }
}
