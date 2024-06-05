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
public class ClassResp {
    @Schema(description = "Class ID", example = "1")
    private Long classId;

    @Schema(description = "Class name", example = "Math 101")
    private String name;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Active status")
    private Boolean active;

    @Schema(description = "List of students")
    private List<StudentResp> students;

    @Schema(description = "List of lessons")
    private List<LessonResp> lessons;
}
