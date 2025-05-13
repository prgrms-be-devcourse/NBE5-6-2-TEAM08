package com.grepp.team08.app.model.place.entity;

import com.grepp.team08.app.model.recommend.code.Dong;
import com.grepp.team08.infra.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Region extends BaseEntity {
    @Id
    private Long regionId; // DB 초기값 미리 세팅 예정
    @Column(nullable = false, length = 10)
    private String city;

    @Column(nullable = false, length = 10)
    private String gu;

    @Enumerated(EnumType.STRING)
    private Dong dong;

}
