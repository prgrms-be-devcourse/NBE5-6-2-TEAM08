package com.grepp.team08.app.model.course.entity;

import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.infra.entity.BaseEntity;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class EditorCourse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long editorCourseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member id;
    private String title;
    private String description;
    //소프트 delete때문에
    private Boolean activated =true;

}
