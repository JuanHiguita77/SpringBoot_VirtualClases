package com.riwi.virtualClasses.infrastructure.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.virtualClasses.api.dto.request.StudentReq;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.domain.entities.Class;
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
    public StudentResp create(StudentReq request) {
        Student student = this.requestToEntity(request);
        return this.entityToResp(this.studentRepository.save(student));
    }

    @Override
    public StudentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public StudentResp update(StudentReq request, Long id) {
        Student student = this.find(id);
        student = this.requestToEntity(request);
        student.setId(id);

        return this.entityToResp(this.studentRepository.save(student));
    }

    @Override
    public void delete(Long id) {
        Student student = this.find(id);
        student.setActive(false);
        this.studentRepository.save(student);
    }

    @Override
    public Page<StudentResp> getAll(String name, String description, int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        Page<Student> studentPage;
        if ((name == null || name.isBlank()) && (description == null || description.isBlank())) {
            studentPage = studentRepository.findByActiveTrue(pagination);
        } else {
            studentPage = studentRepository.findByNameContainingAndActiveTrueOrDescriptionContainingAndActiveTrue(name, description, pagination);
        }

        return studentPage.map(this::entityToResp);    
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
        Class classEntity = this.classRepository.findById(studentReq.getClassId()).orElseThrow(() -> new BadRequestException("No class found with the supplied ID"));;

        return Student.builder()
                .name(studentReq.getName())
                .email(studentReq.getEmail())
                .active(true)
                .classEntity(classEntity)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Student find(Long id) {
        return this.studentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No student found with the supplied ID"));
    }

    @Override
    public Page<StudentResp> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        return null;
    }
}

