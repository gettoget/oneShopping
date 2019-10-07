package com.ldz.biz.controller;

import com.ldz.biz.service.StatisNewService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    /**
     *  单个 已经开奖的奖品统计
     */
    @PostMapping("/dgkj")
    public ApiResponse<Object> dgkj(String time , String proName, String orderBy, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "3") int pageSize){
        return service.dgkj(time, proName, orderBy, pageNum, pageSize);
    }

    /**
     * 单个用户的购买情况
     */
    @PostMapping("/dgyh")
    public ApiResponse<Object> dgyh(String time){
        return service.dgyh(time);
    }

    /**
     * 单个用户的账户变化情况
     */
    @PostMapping("/zhbh")
    public ApiResponse<Object> zhbh(String id ,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize){
        return service.zhbh(id, pageNum, pageSize);
    }

    /**
     *   一类商品购买的情况和浏览情况
     */
    @PostMapping("/kj")
    public ApiResponse<Object> kj(String time, String end , String proName, String orderBy, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "3") int pageSize){
        return service.kj(time, end,proName,orderBy,pageNum,pageSize);
    }

    /**
     * 用户每天购买的数量 排名
     */
    @PostMapping("/yhgm")
    public ApiResponse<Object> yhgm(String time,String end , String name, String orderBy, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        return service.yhgm(time,end,name,orderBy,pageNum,pageSize);
    }

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------  10-07
    /**
     * 新注册用户当日参与率
     * @return
     */
    @PostMapping("/getCyl")
    public ApiResponse<List<String>> getCyl(String day){
        return service.getCyl(day);
    }


    /**
     *  当日充值订单分布  及 今天 充值中 注册一天的人数比例 , 充值二天的人数比例  充值大于等于三天的人数比例
     * @return
     */
    @PostMapping("/getCzfb")
    public ApiResponse<List<Integer>> getCzfb(){
        return service.getCzfb();
    }

    /**
     * 当日新注册用户当日的充值率  (默认三十天)
     * @return
     */
    @PostMapping("/getCzl")
    public ApiResponse<List<String>> getCzl(@RequestParam(defaultValue = "30") String day){
        return service.getCzl(day);
    }

    /**
     *  非新注册用户日活跃度=当日打开APP的非新注册用户/非新总注册用户 （分子、分母均不含当日新注册用户）(日期轴曲线)
     * @return
     */
    @PostMapping("/getHyd")
    public ApiResponse<List<String>> getHyd(@RequestParam(defaultValue = "30") String day){
        return service.getHyd(day);
    }

    /**
     * 非新用户活跃强度=非新注册用户的打开次数/非新注册用户的打开APP的用户数量（分子、分母均不含当日新注册用户）（日期轴曲线）
     *  活跃强度 可能会大于一
     */
    @PostMapping("/getHyqd")
    public ApiResponse<List<String>> getHyqd(@RequestParam(defaultValue =  "30")String day){
        return service.getHyqd(day);
    }







}
