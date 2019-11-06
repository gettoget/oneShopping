package com.ldz.biz.appctrl;

import com.ldz.biz.model.Bank;
import com.ldz.biz.service.BankService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/guest/bank")
public class BankCtrl extends BaseController<Bank,String> {
    @Autowired
    private BankService service;

    @Override
    protected BaseService<Bank, String> getBaseService() {
        return service;
    }

    /**
     * 根据传入的 银行卡类型 随机一张银行卡
     * @param type
     * @return
     */
    @GetMapping("/getBankNo")
    public ApiResponse<Bank> getBankNo(String type){
        return service.getBankNo(type);
    }


}
