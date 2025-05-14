package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.image.dto.ImageDto;
import com.grepp.team08.app.model.place.dto.PlaceDetailDto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CourseDetailDto {
  String title;
  String nickname;
  LocalDateTime createAt;
  String description;
  List<String> imageUrl;
  List<PlaceDetailDto> places;

}
