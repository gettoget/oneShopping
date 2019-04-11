package com.ldz.biz.service;

import com.ldz.biz.model.ProEval;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ProEvalService extends BaseService<ProEval, String> {

    ApiResponse<String> saveEntity(ProEval entity);
}