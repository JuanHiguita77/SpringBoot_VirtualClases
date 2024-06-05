package com.riwi.virtualClasses.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riwi.virtualClasses.api.dto.errors.ErrorsResp;
import com.riwi.virtualClasses.api.dto.request.StudentReq;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.infrastructure.abstract_services.IStudentService;
import com.riwi.virtualClasses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Operation(summary = "List all students", description = "Endpoint to list all students with pagination and search by name")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<Page<StudentResp>> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) SortType sort,
            @RequestParam(required = false) String name) 
            {
        return ResponseEntity.ok(studentService.getAll(name, page, size, sort != null ? sort : SortType.NONE));
    }

    @Operation(summary = "Get student by ID", description = "Endpoint to get a specific student by their ID")
    @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{id}")
    public ResponseEntity<StudentResp> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.studentService.get(id));
    }

    @Operation(summary = "Create a student", description = "Endpoint to create a new student")
    @ApiResponse(responseCode = "404", description = "Invalid Input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<StudentResp> create(@RequestBody StudentReq studentReq) {
        return ResponseEntity.ok(this.studentService.create(studentReq));
    }

    @Operation(summary = "Update an existing student", description = "Endpoint to update an existing student by their ID")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/{id}")
    public ResponseEntity<StudentResp> update(@PathVariable Long id, 
    @RequestBody StudentReq studentReq) {
        return ResponseEntity.ok(this.studentService.update(studentReq, id));
    }

    @Operation(summary = "Disable a student", description = "Endpoint to disable a student by their ID")
    @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PatchMapping("/{id}/disable")
    public ResponseEntity<StudentResp> disable(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(this.studentService.get(id));
    }
}
