package com.ldz.biz.appctrl;

import com.ldz.biz.model.Exchange;
import com.ldz.biz.service.ExchangeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/exchange")
public class ExchangeCtrl extends BaseController<Exchange, String> {

    @Autowired
    private ExchangeService service;

    @Override
    protected BaseService<Exchange, String> getBaseService() {
        return service;
    }
}
