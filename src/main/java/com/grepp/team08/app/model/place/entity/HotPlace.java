package com.grepp.team08.app.model.place.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class HotPlace {

    @Id
    private Long placeId;
    private String placeName;
    private String category;
    private String address;
    private Double latitude;
    private Double longitude;

}
