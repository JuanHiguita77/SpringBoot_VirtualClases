package com.riwi.virtualClasses.infrastructure.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.riwi.virtualClasses.api.dto.request.LessonReq;
import com.riwi.virtualClasses.api.dto.request.MultimediaReq;
import com.riwi.virtualClasses.api.dto.response.LessonResp;
import com.riwi.virtualClasses.api.dto.response.MultimediaResp;
import com.riwi.virtualClasses.domain.entities.Lesson;
import com.riwi.virtualClasses.domain.entities.Multimedia;
import com.riwi.virtualClasses.domain.repositories.LessonRepository;
import com.riwi.virtualClasses.domain.repositories.MultimediaRepository;
import com.riwi.virtualClasses.domain.repositories.ClassRepository;
import com.riwi.virtualClasses.infrastructure.abstract_services.ILessonService;
import com.riwi.virtualClasses.utils.enums.SortType;
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

    @Autowired
    private final MultimediaRepository multimediaRepository;

    @Override
    public LessonResp create(LessonReq request) {
        Lesson initialLesson = this.requestToEntity(request);
        Lesson savedLesson = this.lessonRepository.save(initialLesson);

        List<Multimedia> multimediaList = request.getMultimediaList().stream()
                .map(multimediaReq -> {
                    Multimedia multimedia = this.requestToEntity(multimediaReq, savedLesson);
                    return this.multimediaRepository.save(multimedia);
                })
                .collect(Collectors.toList());

        savedLesson.setMultimediaList(multimediaList);
        return this.entityToResp(savedLesson);
    }

    @Override
    public LessonResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public void delete(Long id) {
        Lesson lesson = this.find(id);
        lesson.setActive(false);
        this.lessonRepository.save(lesson);

        lesson.getMultimediaList().forEach(multimedia -> {
            multimedia.setActive(false);
            this.multimediaRepository.save(multimedia);
        });
    }

    private LessonResp entityToResp(Lesson entity) {
        List<MultimediaResp> multimediaList = entity.getMultimediaList().stream()
                .map(this::entityToResp)
                .collect(Collectors.toList());

        return LessonResp.builder()
                .lessonId(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .classId(entity.getClassEntity().getId())
                .multimediaList(multimediaList)
                .build();   
    }

    private MultimediaResp entityToResp(Multimedia entity) {
        return MultimediaResp.builder()
                .multimediaId(entity.getId())
                .type(entity.getType())
                .url(entity.getUrl())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .lessonId(entity.getLesson().getId())
                .build();
    }

    private Lesson requestToEntity(LessonReq lessonReq) {
        Class classEntity = this.classRepository.findById(lessonReq.getClassId())
                .orElseThrow(() -> new BadRequestException("No class found with the supplied ID"));

        return Lesson.builder()
                .title(lessonReq.getTitle())
                .content(lessonReq.getContent())
                .active(true)
                .classEntity(classEntity)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Multimedia requestToEntity(MultimediaReq multimediaReq, Lesson lesson) {
        return Multimedia.builder()
                .type(multimediaReq.getType())
                .url(multimediaReq.getUrl())
                .active(true)
                .lesson(lesson)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Lesson find(Long id) {
        return this.lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No lesson found with the supplied ID"));
    }

    @Override
    public Page<LessonResp> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LessonResp update(LessonReq request, Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}


