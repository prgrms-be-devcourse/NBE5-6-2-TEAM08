package com.grepp.team08.app.controller.api.recommend;

import com.grepp.team08.app.model.place.dto.PlaceDto;
import com.grepp.team08.app.model.recommend.dto.RecommendDto;
import com.grepp.team08.app.model.recommend.service.RecommendService;
import com.grepp.team08.infra.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/llm/recommend")
@Validated
public class RecommendApiController {

    private final RecommendService recommendService;

    @PostMapping("/course")
    public ResponseEntity<List<PlaceDto>> dateCourse(
        @Validated @RequestBody RecommendDto dto) {

        List<PlaceDto> places = recommendService.recommendDateCourse(dto);
        return ResponseEntity.ok(places);
    }

}
