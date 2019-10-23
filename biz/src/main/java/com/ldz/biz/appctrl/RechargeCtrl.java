package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取充值渠道
     * @return
     */
    @GetMapping("/getChannel")
    public ApiResponse<List<Map>> getPaymentChannel(){
        return service.getPaymentChannel();
    }

    /**
     * 查询充值订单是否付款成功
     */
    @PostMapping("/checkPay")
    public ApiResponse<String> checkPayment(String id){
        return service.checkPayment(id);
    }

    /**
     *  查询用户充值次数
     */
    @GetMapping("/getRechargeNum")
    public ApiResponse<String> getRechargeNum(){
        return service.getRechargeNum();
    }

    /**
     *    用户五星好评送 2 GCoin
     */
    @PostMapping("/highPraise")
    public ApiResponse<String> saveHighPraise(){
        return service.saveHighPraise();
    }

    @GetMapping("/getPraise")
    public ApiResponse<Integer> getPraise(){
        return service.getPraise();
    }








}
