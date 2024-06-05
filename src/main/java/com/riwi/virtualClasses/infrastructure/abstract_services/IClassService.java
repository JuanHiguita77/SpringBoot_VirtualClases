package com.riwi.virtualClasses.infrastructure.abstract_services;

import com.riwi.virtualClasses.api.dto.request.ClassReq;
import com.riwi.virtualClasses.api.dto.response.ClassResp;
import com.riwi.virtualClasses.infrastructure.services.CrudService;

public interface IClassService extends CrudService<ClassReq, ClassResp, Long>{

    public String FIELD_BY_SORT = "ClassName";

    public 
}
