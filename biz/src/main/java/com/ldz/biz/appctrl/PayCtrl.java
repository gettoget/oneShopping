package com.ldz.biz.appctrl;

import com.ldz.biz.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/paySuc")
    public String paySuc(String amount, String trans_id, String words){
        return service.paySuc(amount,trans_id,words);
    }

}
