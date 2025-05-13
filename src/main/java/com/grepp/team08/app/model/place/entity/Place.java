package com.grepp.team08.app.model.place.entity;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.infra.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Place extends BaseEntity {
    @Id
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id")
    private Course courseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "editor_course_id")
    private EditorCourse editorCourseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region regionId;

    @Column(nullable = false, length = 255)
    private String placeName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 255)
    private String placeUrl;

    private double latitude;
    private double longitude;

}
