package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.RefferRecord;
import com.ldz.biz.service.RefferRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refferrecord")
public class RefferRecordContrl extends BaseController<RefferRecord,String> {

    @Autowired
    private RefferRecordService service;


    @Override
    protected BaseService<RefferRecord, String> getBaseService() {
        return service;
    }
}
