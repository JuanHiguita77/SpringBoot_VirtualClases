package com.riwi.virtualClasses.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResp {
    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;

    @Schema(description = "Lesson title", example = "Introduction to Algebra")
    private String title;

    @Schema(description = "Content")
    private String content;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Active status")
    private Boolean active;

    @Schema(description = "Class ID", example = "1")
    private Long classId;

    @Schema(description = "List of multimedia")
    private List<MultimediaResp> multimediaList;
}
