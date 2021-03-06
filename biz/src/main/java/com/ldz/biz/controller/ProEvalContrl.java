package com.ldz.biz.controller;

import com.ldz.biz.model.ProEval;
import com.ldz.biz.service.ProEvalService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proeval")
public class ProEvalContrl extends BaseController<ProEval,String> {

    @Autowired
    private ProEvalService service;


    @Override
    protected BaseService<ProEval, String> getBaseService() {
        return service;
    }

    @PostMapping("/saveEval")
    public ApiResponse<String> save(ProEval entity){
        return service.saveEval(entity);
    }
}
