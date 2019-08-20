package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Question;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface QuestionService  extends BaseService<Question,String> {
    ApiResponse<String> saveQuestion(Question entity);

    PageResponse<Question> getNewPager(Page<Question> page);

    ApiResponse<String> delete(String id);

    ApiResponse<String> updateEntity(Question entity);

    ApiResponse<String> getProGroup(int pageNum, int pageSize, String name);

    ApiResponse<String> getOneMess(int pageNum, int pageSize, String userId);
}
