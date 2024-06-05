package com.riwi.virtualClasses.infrastructure.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.virtualClasses.api.dto.request.LessonReq;
import com.riwi.virtualClasses.api.dto.response.LessonResp;
import com.riwi.virtualClasses.domain.entities.Lesson;
import com.riwi.virtualClasses.domain.repositories.LessonRepository;
import com.riwi.virtualClasses.domain.repositories.ClassRepository;
import com.riwi.virtualClasses.infrastructure.abstract_services.ILessonService;
import com.riwi.virtualClasses.utils.exceptions.BadRequestException;
import com.riwi.virtualClasses.domain.entities.Class;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final ClassRepository classRepository;

    @Override
    public LessonResp create(LessonReq request) {
        Lesson newLesson = this.requestToEntity(request);
        return this.entityToResp(this.lessonRepository.save(newLesson));
    }

   

    private LessonResp entityToResp(Lesson entity) {
        return LessonResp.builder()
                .lessonId(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .classId(entity.getClassEntity().getId())
                .build();
    }

    private Lesson requestToEntity(LessonReq lessonReq) {
        Class classEntity = this.classRepository.findById(lessonReq.getClassId()).orElseThrow(() -> new BadRequestException("No class found with the supplied ID"));

        return Lesson.builder()
                .title(lessonReq.getTitle())
                .content(lessonReq.getContent())
                .active(true)
                .classEntity(classEntity)
                .createdAt(LocalDateTime.now())
                .build();
    }


}

