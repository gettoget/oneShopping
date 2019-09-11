package com.ldz.biz.appctrl;

import com.ldz.biz.model.Shared;
import com.ldz.biz.service.SharedService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/shared")
public class SharedCtrl extends BaseController<Shared,String> {
    @Autowired
    private SharedService service;

    @Override
    protected BaseService<Shared, String> getBaseService() {
        return service;
    }

    @PostMapping("/save")
    public ApiResponse<String> save(Shared shared){
        return service.validAndSave(shared);
    }

}
