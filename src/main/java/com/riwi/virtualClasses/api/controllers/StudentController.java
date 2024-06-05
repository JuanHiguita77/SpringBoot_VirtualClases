package com.riwi.virtualClasses.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riwi.virtualClasses.api.dto.request.StudentReq;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.infrastructure.abstract_services.IStudentService;
import com.riwi.virtualClasses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public ResponseEntity<Page<StudentResp>> getAll(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) SortType sort, @RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(this.studentService.searchByName(name, page, size));
        }
        
        return ResponseEntity.ok(studentService.getAll(page, size, sort != null ? sort : SortType.NONE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResp> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.studentService.get(id));
    }

    @PostMapping
    public ResponseEntity<StudentResp> create(@RequestBody StudentReq studentReq) {
        return ResponseEntity.ok(this.studentService.create(studentReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResp> update(@PathVariable Long id, @RequestBody StudentReq studentReq) {
        return ResponseEntity.ok(this.studentService.update(studentReq, id));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<StudentResp> disable(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(this.studentService.get(id));
    }
}
