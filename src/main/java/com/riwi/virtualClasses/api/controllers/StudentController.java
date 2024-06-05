package com.riwi.virtualClasses.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<StudentResp> getAll(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) SortType sort, @RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return this.studentService.searchByName(name, page, size);
        }
        
        return studentService.getAll(page, size, sort != null ? sort : SortType.NONE);
    }

    @GetMapping("/{id}")
    public StudentResp get(@PathVariable Long id) {
        return this.studentService.get(id);
    }


}
