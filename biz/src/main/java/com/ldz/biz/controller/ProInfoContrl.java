package com.ldz.biz.controller;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proinfo")
public class ProInfoContrl extends BaseController<ProInfo, String> {

    @Autowired
    private ProInfoService service;

    @Override
    protected BaseService<ProInfo, String> getBaseService() {
        return service;
    }

    /**
     * 手动上架一个商品
     * @param id
     * @return
     */
    @PostMapping("/saveOne")
    public ApiResponse<String> saveOne(String id){
        return service.saveOne(id);
    }

    /**
     * 查询商品上一次中奖的信息
     * @param baseid 商品基本信息的id
     */
    @PostMapping("/getLatestPerson")
    public ApiResponse<ProInfo> getLatestPerson(String baseid){
        return service.getLatestPerson(baseid);
    }










}
