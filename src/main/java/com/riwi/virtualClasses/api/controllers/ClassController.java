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

@Tag(name = "Classes")
@RestController
@RequestMapping("/class")
@AllArgsConstructor
public class ClassController {

    @Autowired
    private final IClassService classService;

    @Operation(summary = "List all classes", description = "Endpoint to list all classes with pagination and search by name/description")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<Page<ClassResp>> getAllClasses(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "NONE") SortType sortType,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String description) 
    {
        return ResponseEntity.ok(this.classService.getAll(page - 1, size, sortType, name, description));
    }

    @Operation(summary = "Get class information", description = "Endpoint to get detailed information of a specific class by ID")
    @ApiResponse(responseCode = "400", description = "Invalid class ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ClassResp> getClassInfo(@PathVariable("id") Long classId) {
        return ResponseEntity.ok(this.classService.get(classId));
    }

    @Operation(summary = "Create a new class", description = "Endpoint to create a new class")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<ClassResp> createClass(@Validated @RequestBody ClassReq request) {
        return ResponseEntity.ok(this.classService.create(request));
    }
}

