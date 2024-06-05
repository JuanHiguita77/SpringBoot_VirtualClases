package com.riwi.virtualClasses.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaReq {
    @NotBlank
    private String type;

    private String url;

    @NotNull
    private Long lessonId;
    
    private Boolean active;
}
