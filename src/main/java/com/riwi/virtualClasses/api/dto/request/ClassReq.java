package com.riwi.virtualClasses.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassReq {
    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;
    private Boolean active;
}
