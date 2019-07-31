package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/recharge")
public class RechargeCtrl  {

    @Autowired
    private RechargeService service;



    /**
     * 用户充值接口
     * @param amount
     * @return
     */
    @PostMapping("/addBalance")
    public ApiResponse<String> recharge(int amount){
        return service.saveRecharge(amount);
    }

    /**
     * 用户充值记录
     */
    @GetMapping("/newPager")
    public PageResponse<Recharge> gerNewPager(Page<Recharge> page){
        return service.getNewPager(page);

    }







}
