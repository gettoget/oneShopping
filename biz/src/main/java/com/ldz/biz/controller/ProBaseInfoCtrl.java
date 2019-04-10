package com.ldz.biz.controller;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/probaseinfo")
public class ProBaseInfoCtrl extends BaseController<ProBaseinfo, String> {

    @Autowired
    private ProBaseinfoService service;

    @Override
    protected BaseService<ProBaseinfo, String> getBaseService() {
        return service;
    }

    @Override
    public ApiResponse<String> save(ProBaseinfo entity){
        return service.saveEntity(entity);
    }
}
