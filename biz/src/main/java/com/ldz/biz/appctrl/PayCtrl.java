package com.ldz.biz.appctrl;

import com.alibaba.fastjson.JSON;
import com.ldz.biz.bean.PaySuc;
import com.ldz.biz.service.RechargeService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/pay")
public class PayCtrl {

    @Autowired
    private RechargeService service;

    /**
     * 支付回调接口
     * @return
     */
    @PostMapping(value = "/paySuc")
    public ApiResponse<String> paySuc( String data){
        RuntimeCheck.ifBlank(data, "DATA IS NULL");
        PaySuc suc = JSON.parseObject(data, PaySuc.class);
        return service.paySuc(suc.getAmount(),suc.getTrans_id(),suc.getWords(),data);
    }

    @PostMapping("/paySucTest")
    public ApiResponse<String> paySucTest(String id){
        return service.paySucTest(id);
    }

}
