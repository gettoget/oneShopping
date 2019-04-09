package com.ldz.biz.controller;

import com.ldz.biz.model.Popularimgs;
import com.ldz.biz.service.PopularimgsService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/popularimgs")
public class PopularImgCtrl extends BaseController<Popularimgs,String> {

    @Autowired
    private PopularimgsService service;

    @Override
    protected BaseService<Popularimgs, String> getBaseService() {
        return service;
    }
}
