package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Store;
import com.ldz.biz.service.StoreService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/user/store")
public class StoreCtrl  {

    @Autowired
    private StoreService service;

    @PostMapping("/save")
    public ApiResponse<String> save(Store entity){
        return service.saveEntity(entity);
    }

    @GetMapping("/newPager")
    public PageResponse<Store> newPager(Page<Store> page){
        return service.getNewPager(page);
    }

    @PostMapping("/remove/{pkid}")
    public ApiResponse<String> storeCanel(@PathVariable("pkid") String id){
        return service.updateStoreCancel(id);
    }





}
