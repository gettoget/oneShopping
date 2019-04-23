package com.ldz.biz.appctrl;

import com.ldz.biz.model.Store;
import com.ldz.biz.service.StoreService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/store")
public class StoreCtrl extends BaseController<Store,String> {

    @Autowired
    private StoreService service;

    @Override
    protected BaseService<Store, String> getBaseService() {
        return service;
    }
}
