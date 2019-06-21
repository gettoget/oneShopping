package com.ldz.biz.controller;

import com.ldz.biz.model.Question;
import com.ldz.biz.service.QuestionService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionContrl extends BaseController<Question,String> {

    @Autowired
    private QuestionService service;


    @Override
    protected BaseService<Question, String> getBaseService() {
        return service;
    }


    @PostMapping("/update")
    public ApiResponse<String> update(Question entity){
        return service.updateEntity(entity);
    }

}
