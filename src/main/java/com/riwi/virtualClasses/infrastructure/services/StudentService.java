package com.riwi.virtualClasses.infrastructure.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.virtualClasses.api.dto.request.StudentReq;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.domain.entities.Student;
import com.riwi.virtualClasses.domain.repositories.StudentRepository;
import com.riwi.virtualClasses.domain.repositories.ClassRepository;
import com.riwi.virtualClasses.infrastructure.abstract_services.IStudentService;
import com.riwi.virtualClasses.utils.enums.SortType;
import com.riwi.virtualClasses.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {

    private static final String FIELD_BY_SORT = "name";

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final ClassRepository classRepository;

   
    @Override
    public Page<StudentResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.studentRepository.findAll(pagination).map(this::entityToResp);
    }


    private StudentResp entityToResp(Student entity) {
        return StudentResp.builder()
                .studentId(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .classId(entity.getClassEntity().getId())
                .build();
    }

    private Student requestToEntity(StudentReq studentReq) {
        Class class = this.classRepository.findById(studentReq.getClassId()).orElseThrow(() -> new BadRequestException("No Class found with the supplied ID"));;

        return Student.builder()
                .name(studentReq.getName())
                .email(studentReq.getEmail())
                .active(true)
                .class(class)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Student find(Long id) {
        return this.studentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No student found with the supplied ID"));
    }
}

