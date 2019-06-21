package com.ldz.biz.controller;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/probaseinfo")
public class ProBaseInfoContrl extends BaseController<ProBaseinfo, String> {

    @Autowired
    private ProBaseinfoService service;

    @Override
    protected BaseService<ProBaseinfo, String> getBaseService() {
        return service;
    }

    /**
     * 新增商品基本信息
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ProBaseinfo entity){
        return service.saveEntity(entity);
    }
}
