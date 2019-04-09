package com.ldz.biz.controller;

import com.ldz.biz.model.RefferRecord;
import com.ldz.biz.service.RefferRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refferrecord")
public class RefferRecordCtrl extends BaseController<RefferRecord, String> {

    @Autowired
    private RefferRecordService service;

    @Override
    protected BaseService<RefferRecord, String> getBaseService() {
        return service;
    }
}
