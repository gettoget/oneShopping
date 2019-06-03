package com.ldz.biz.controller;

import com.ldz.biz.service.ProInfoService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub")
public class TestController {

    @Autowired
    private ProInfoService infoService;

    /**
     * 避免清空redis的情况 , 调用方法补全号码
     */
    @GetMapping("/complete")
    public ApiResponse<String> complete(String id){
        return infoService.complete(id);
    }


}
