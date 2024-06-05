package com.riwi.virtualClasses.infrastructure.abstract_services;

import com.riwi.virtualClasses.api.dto.request.LessonReq;
import com.riwi.virtualClasses.api.dto.response.LessonResp;
import com.riwi.virtualClasses.infrastructure.services.CrudService;

public interface ILessonService extends CrudService<LessonReq, LessonResp, Long>{
}

