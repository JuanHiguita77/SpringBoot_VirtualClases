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
public class StudentResp {
    @Schema(description = "Student ID", example = "1")
    private Long studentId;

    @Schema(description = "Student name", example = "John Doe")
    private String name;

    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Active status")
    private Boolean active;

    @Schema(description = "Class ID", example = "1")
    private Long classId;
}

