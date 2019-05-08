package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ReceiveAddr;
import com.ldz.biz.service.ReceiveAddrService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/user/receiveaddr")
public class ReceiveAddrCtrl  {

    @Autowired
    private ReceiveAddrService service;



    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ReceiveAddr entity){
            return service.saveEntity(entity);
    }

    @GetMapping("/newPager")
    public PageResponse<ReceiveAddr> getNewPager(Page<ReceiveAddr>page){
        return service.getNewPager(page);
    }

    @PostMapping("/remove/{pkId}")
    public ApiResponse<String> removeEntity(@PathVariable("pkId")String id){
        return service.removeEntity(id);
    }

    @PostMapping("/update")
    public ApiResponse<String> updateEntity(ReceiveAddr entity){
        return service.updateEntity(entity);
    }






}
