package com.grepp.team08.app.model.recommend.dto;

import com.grepp.team08.app.model.recommend.code.Category;
import com.grepp.team08.app.model.recommend.code.Dong;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecommendDto(
//    @NotBlank
//    String date, // 봐서 빨까말까
    @Size(min = 1, max = 3)
    List<Category> categories,
    @NotNull Dong dong) {
}
