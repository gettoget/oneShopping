package com.ldz.biz.controller;

import com.ldz.biz.model.Exchange;
import com.ldz.biz.service.ExchangeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeContrl extends BaseController<Exchange,String> {

    @Autowired
    private ExchangeService service;



    @Override
    protected BaseService<Exchange, String> getBaseService() {
        return service;
    }


    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(Exchange entity){
        return ApiResponse.success();
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return ApiResponse.success();
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(Exchange entity){
        return ApiResponse.success();
    }
}
