package com.riwi.virtualClasses.infrastructure.abstract_services;

import org.springframework.data.domain.Page;

import com.riwi.virtualClasses.api.dto.request.StudentReq;
import com.riwi.virtualClasses.api.dto.response.StudentResp;
import com.riwi.virtualClasses.infrastructure.services.CrudService;
import com.riwi.virtualClasses.utils.enums.SortType;

public interface IStudentService extends CrudService<StudentReq, StudentResp, Long> {
    public String FIELD_BY_SORT = "name";

    Page<StudentResp> getAll(String name, int page, int size, SortType sortType);
}
