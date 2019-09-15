package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ProEval;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface ProEvalService extends BaseService<ProEval, String> {

    ApiResponse<String> saveEntity(ProEval entity);

    PageResponse<ProEval> getNewPager(Page<ProEval> page);

    ApiResponse<String> saveThumbs(String id);

    ApiResponse<String> saveEval(ProEval entity);
}