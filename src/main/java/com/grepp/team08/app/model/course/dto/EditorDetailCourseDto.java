package com.grepp.team08.app.model.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grepp.team08.app.model.place.dto.PlaceDetailDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EditorDetailCourseDto {
  String title;
  String nickname;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime createAt;
  String description;
  List<String> imageUrl;
  List<PlaceDetailDto> places;
}
