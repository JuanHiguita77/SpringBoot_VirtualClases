package com.riwi.virtualClasses.api.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaResp {
    @Schema(description = "Multimedia ID", example = "1")
    private Long multimediaId;

    @Schema(description = "Type", example = "video")
    private String type;

    @Schema(description = "URL", example = "http://example.com/video.mp4")
    private String url;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Active status")
    private Boolean active;

    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;
}
