package com.ldz.biz.controller;

import com.ldz.biz.service.StatisNewService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statisnew")
public class StatisNewControl  {

    @Autowired
    private StatisNewService service;

    /**
     * 统计今日 当月 累计充值金额 (分 3 类  充值未成功  ,  充值成功 ,  系统送的 )
     */
    @PostMapping("/statisNewRecharge")
    public ApiResponse<Map<String, Object>> statisNewRecharge(String time){
            return service.statisNewRecharge(time);
    }

    /**
     * 统计 今日 当月 累计消费金币数
     */
    @PostMapping("/statisNewCharrge")
    public ApiResponse<Map<String, Object>> statisNewCharge(){
        return service.statisNewCharge();
    }


    @PostMapping("/statisNewWater")
    public ApiResponse<List<String>> statisNewWater(String day){
        return service.statisNewWater(day);
    }

    /**
     * 新增用户统计
     * @param day
     * @return
     */
    @PostMapping("/statisNewUser")
    public ApiResponse<List<String>> statisNewUser(String day){
        return service.statisNewUser(day);
    }

    /**
     *
     * @return
     */
    @PostMapping("/statisCzqd")
    public ApiResponse<List<String>> statisCzqd(String day){
        return service.statisCzqd(day);
    }

    @PostMapping("/statisAllQd")
    public ApiResponse<Map<String, String>> statisAllQd(String day){
        return service.statisAllQd(day);
    }

    @PostMapping("/statisQdJe")
    public ApiResponse<Map<String,String>> statisQdJe(){
        return service.statisQdJe();
    }


    /**
     * 新增用户统计 , 今日 本月 累计
     */
    public ApiResponse<Map<String,String>> statisXzYh(){
        return service.statisXzYh();
    }

    /**
     *  商品购买 浏览  统计接口
     */
    @PostMapping("/spgm")
    public ApiResponse<Object>  spgm(){
        return service.spgm();
    }

    /**
     * 查看某一天 开奖商品的浏览数和购买数 和 比例
     */
    public ApiResponse<Object> dtLlGm(String time , String id){
        return service.dtLlGm(time, id);
    }


















}
