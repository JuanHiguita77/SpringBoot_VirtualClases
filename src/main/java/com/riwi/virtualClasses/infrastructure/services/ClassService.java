package com.riwi.virtualClasses.infrastructure.services;

import com.riwi.virtualClasses.api.dto.request.ClassReq;
import com.riwi.virtualClasses.api.dto.response.ClassResp;
import com.riwi.virtualClasses.api.dto.response.LessonResp;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.domain.entities.Class;
import com.riwi.virtualClasses.domain.entities.Lesson;
import com.riwi.virtualClasses.domain.entities.Student;
import com.riwi.virtualClasses.domain.repositories.ClassRepository;
import com.riwi.virtualClasses.infrastructure.abstract_services.IClassService;
import com.riwi.virtualClasses.utils.enums.SortType;
import com.riwi.virtualClasses.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ClassService implements IClassService {

    private static final String FIELD_BY_SORT = "ClassName";

    @Autowired
    private final ClassRepository classRepository;

    @Override
    public ClassResp create(ClassReq request) {
        Class newClass = this.requestToEntity(request);
        return this.entityToResp(this.classRepository.save(newClass));
    }

    @Override
    public ClassResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public Page<ClassResp> getAll(int page, int size, SortType sortType, String name, String description) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        Page<Class> classPage;
        if ((name == null || name.isEmpty()) && (description == null || description.isEmpty())) {
            classPage = classRepository.findByActiveTrue(pagination);
        } else {
            classPage = classRepository.findByNameContainingAndActiveTrueOrDescriptionContainingAndActiveTrue(
                    name != null ? name : "",
                    description != null ? description : "",
                    pagination
            );
        }

        return classPage.map(this::entityToResp);
    }

    private ClassResp entityToResp(Class entity) {
        // Implementar la conversión de entity a ClassResp
        return ClassResp.builder()
                .classId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .active(entity.getActive())
                .students(entity.getStudents().stream().map(this::studentEntityToResp).toList())
                .lessons(entity.getLessons().stream().map(this::lessonEntityToResp).toList())
                .build();
    }

    private StudentResp studentEntityToResp(Student student) {
        return StudentResp.builder()
                .studentId(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .active(student.getActive())
                .build();
    }

    private LessonResp lessonEntityToResp(Lesson lesson) {
        return LessonResp.builder()
                .lessonId(lesson.getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .createdAt(lesson.getCreatedAt())
                .active(lesson.getActive())
                .build();
    }

    private Class requestToEntity(ClassReq classReq) {
        return Class.builder()
                .name(classReq.getName())
                .description(classReq.getDescription())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    

    private Class find(Long id) {
        return this.classRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No class found with the supplied ID"));
    }

    @Override
    public Page<ClassResp> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ClassResp update(ClassReq request, Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
