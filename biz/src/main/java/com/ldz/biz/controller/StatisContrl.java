package com.ldz.biz.controller;


import com.ldz.biz.service.StatisService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PastOrPresent;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statis")
public class StatisContrl {

    @Autowired
    private StatisService service;

    /**
     * 用户统计
     */
    @PostMapping("/statisUser")
    public ApiResponse<Map<String,Integer>> statisUser(){
        return service.statisUser();
    }


    /**
     * 充值统计
     */
    @PostMapping("/statisRecharge")
    public ApiResponse<Map<String, Integer>>  statisRecharge(){
        return service.statisRecharge();
    }

    /**
     * 消费统计
     */
    @PostMapping("/statisCharge")
    public ApiResponse<Map<String, Integer>> statisCharge(){
        return service.statisCharge();
    }

    /**
     * 当日产品统计
     */
    @PostMapping("/statisPro")
    public ApiResponse<Map<String,Integer>> statisPro(){
        return service.statisPro();
    }


    /**
     *  按时间  充值流水
     */
    @PostMapping("/rechargeWater")
    public ApiResponse<List<String>> rechargeWater(String day){
        return service.rechargeWater(day);
    }

    /**
     * 按时间 消费流水
     */
    @PostMapping("/exchargeWater")
    public ApiResponse<List<String>> exchargeWater(String day){
        return service.exchargeWater(day);
    }

    /**
     * 按时间  充值流水 和 消费流水
     */
    @PostMapping("/paymentWater")
    public ApiResponse<List<String>> paymentWater(String day){
        return service.paymentWater(day);
    }





}
