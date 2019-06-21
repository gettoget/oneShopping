package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Exchange;
import com.ldz.biz.service.ExchangeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/exchange")
public class ExchangeCtrl  {

    @Autowired
    private ExchangeService service;

    /**
     * 消费记录分页接口
     * @param page
     * @return
     */
    @GetMapping("/newPager")
    public PageResponse<Exchange> getNewPager(Page<Exchange> page){
        return service.getNewPager(page);
    }

}
