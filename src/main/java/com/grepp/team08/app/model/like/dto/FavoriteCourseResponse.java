package com.grepp.team08.app.model.like.dto;

public record FavoriteCourseResponse(
    Long favoriteCourseId,
    String nickname,
    Long recommendCourseId,
    Long editorCourseId,
    String title
) {}
