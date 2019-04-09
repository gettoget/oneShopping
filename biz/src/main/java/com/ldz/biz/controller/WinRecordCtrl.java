package com.ldz.biz.controller;

import com.ldz.biz.model.WinRecord;
import com.ldz.biz.service.WinRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/winrecord")
public class WinRecordCtrl extends BaseController<WinRecord,String> {

    @Autowired
    private WinRecordService service;

    @Override
    protected BaseService<WinRecord, String> getBaseService() {
        return service;
    }
}
