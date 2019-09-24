package com.ldz.biz.controller;


import com.ldz.biz.service.StatisService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 充值统计
     * @return
     */
    @PostMapping("/rechargeNum")
    public ApiResponse<Map<String, Long>> rechargeNum(){
        return service.rechargeNum();
    }

    /**
     * 充值渠道统计
     * @return
     */
    @PostMapping("/rechargeChannel")
    public ApiResponse<Map<String, Object>> rechargeChannel(){
        return service.rechargeChannel();
    }

    /**
     * 充值趋势统计
     */
    @PostMapping("/rechargeTrend")
    public ApiResponse<List<Map<String,Long>>> rechargeTrend(){
        return service.rechargeTrend();
    }


    /**
     * 用户消费走势 查当天
     */
    @PostMapping("/statisXf")
    public ApiResponse<Map<String,String>> statisXf(String time){
        return service.statisXf(time);
    }

    /**
     * 消费金额走势  默认查一个月
     */
    @PostMapping("/statisXfje")
    public ApiResponse<List<String>> statisXfje(String day){
        return service.statisXfje(day);
    }

    /**
     * 充值渠道统计
     */
    @PostMapping("/statisCzqd")
    public ApiResponse<Map<String,String>> statisCzqd(String time){
        return service.statisCzqd(time);
    }

    /**
     * 充值走势
     * @return
     */
    @PostMapping("/statisCzjb")
    public ApiResponse<List<String>> statisCzjb(String time){
        return service.statisCzjb(time);
    }

    /**
     *  近七天 充值渠道走势
     */
    @PostMapping("/statisLastSeven")
    public ApiResponse<List<String>> statisLastSeven(String day){
        return service.statisLastSeven(day);
    }

    /**
     *  充值成功金额走势
     */
    @PostMapping("/statisCz")
    public ApiResponse<List<String>> statisCz(String day){
        return  service.statisCz(day);
    }

    /**
     *  充值失败金额走势
     */
    @PostMapping("/statisCzsb")
    public ApiResponse<List<String>> statisCzsb(String day){
        return  service.statisCzsb(day);
    }

    /**
     * 新增用户走势
     */
    @PostMapping("/statisNewUser")
    public ApiResponse<List<String>> statisNewUser(String day){
        return service.statisNewUser(day);
    }




}
