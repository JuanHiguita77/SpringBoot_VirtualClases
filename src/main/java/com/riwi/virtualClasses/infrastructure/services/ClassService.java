package com.riwi.virtualClasses.infrastructure.services;

import com.riwi.virtualClasses.api.dto.request.ClassReq;
import com.riwi.virtualClasses.api.dto.response.ClassResp;
import com.riwi.virtualClasses.domain.entities.Class;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassService implements IClassService {

    private static final String FIELD_BY_SORT = "ClassName";

    @Autowired
    private final ClassRepository classRepository;

 

    @Override
    public Page<ClassResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.classRepository.findAll(pagination).map(this::entityToResp);
    }


    private ClassResp entityToResp(Class entity) {
        return ClassResp.builder()
                .classId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
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

}
