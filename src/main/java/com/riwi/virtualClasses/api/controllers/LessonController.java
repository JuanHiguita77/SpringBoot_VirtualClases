package com.riwi.virtualClasses.api.controllers;

import com.riwi.virtualClasses.api.dto.errors.ErrorsResp;
import com.riwi.virtualClasses.api.dto.request.LessonReq;
import com.riwi.virtualClasses.api.dto.response.LessonResp;
import com.riwi.virtualClasses.infrastructure.abstract_services.ILessonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Lessons")
@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    @Autowired
    private final ILessonService lessonService;

    @Operation(summary = "Create a new lesson", description = "Endpoint to create a new lesson")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<LessonResp> createLesson(@Validated @RequestBody LessonReq request) {
        return ResponseEntity.ok(this.lessonService.create(request));
    }

}

