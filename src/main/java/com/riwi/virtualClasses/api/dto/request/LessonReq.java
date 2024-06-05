package com.riwi.virtualClasses.api.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonReq {
    @NotBlank
    @Size(max = 255)
    private String title;

    private String content;

    @NotNull
    private Long classId;
    
    private Boolean active;

    @NotNull
    private List<MultimediaReq> multimediaList;
}
