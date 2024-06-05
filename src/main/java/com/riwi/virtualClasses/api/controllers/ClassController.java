package com.riwi.virtualClasses.api.controllers;

import com.riwi.virtualClasses.api.dto.errors.ErrorsResp;
import com.riwi.virtualClasses.api.dto.request.ClassReq;
import com.riwi.virtualClasses.api.dto.response.ClassResp;
import com.riwi.virtualClasses.infrastructure.abstract_services.IClassService;
import com.riwi.virtualClasses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Classes")
@RestController
@RequestMapping("/api/v1/class")
@AllArgsConstructor
public class ClassController {

    @Autowired
    private final IClassService classService;

    @Operation(summary = "List all classes", description = "Endpoint to list all classes with pagination and search by name/description")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<Page<ClassResp>> getAllClasses(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) SortType sortype) {
        return ResponseEntity.ok(this.classService.getAll(page, size, sortype));
    }

    
}

