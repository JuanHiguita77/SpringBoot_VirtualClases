package com.riwi.virtualClasses.infrastructure.abstract_services;

import org.springframework.data.domain.Page;

import com.riwi.virtualClasses.api.dto.request.ClassReq;
import com.riwi.virtualClasses.api.dto.response.ClassResp;
import com.riwi.virtualClasses.infrastructure.services.CrudService;
import com.riwi.virtualClasses.utils.enums.SortType;

public interface IClassService extends CrudService<ClassReq, ClassResp, Long>{

    public String FIELD_BY_SORT = "ClassName";

    Page<ClassResp> getAll(int page, int size, SortType sortType, String name, String description);
}
