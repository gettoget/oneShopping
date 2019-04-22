package com.ldz.biz.appctrl;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/guest/probaseinfo")
public class ProBaseInfoCtrl extends BaseController<ProBaseinfo, String> {

    @Autowired
    private ProBaseinfoService service;

    @Override
    protected BaseService<ProBaseinfo, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(ProBaseinfo entity){
        return null;
    }




}
