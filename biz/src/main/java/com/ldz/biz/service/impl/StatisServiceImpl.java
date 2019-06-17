package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.StatisService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisServiceImpl implements StatisService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private ProInfoService  proInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProInfoMapper infoMapper;

    @Override
    public ApiResponse<Map<String, Integer>> statisUser() {
        DateTime now = DateTime.now();
        String today = now.toString("yyyy-MM-dd");
        String mon = now.toString("yyyy-MM");
        Map<String, Integer> map = userMapper.statisUser(today, mon);
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<Map<String, Integer>> statisRecharge() {
        DateTime now = DateTime.now();
        String today = now.toString("yyyy-MM-dd");
        String mon = now.toString("yyyy-MM");
        Map<String, Integer> map = rechargeMapper.statisRecharge(today, mon);
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<Map<String, Integer>> statisCharge() {
        DateTime now = DateTime.now();
        String today = now.toString("yyyy-MM-dd");
        String mon = now.toString("yyyy-MM");
        Map<String, Integer> map = exchangeMapper.statisExCharge(today, mon);
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<Map<String, Integer>> statisPro() {
        String date = DateTime.now().toString("yyyy-MM-dd");
        // 今日上架商品
        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        condition.startWith(ProInfo.InnerColumn.cjsj, date);
        Integer sj = proInfoService.countByCondition(condition);
        // 今日开奖商品
        condition = new SimpleCondition(ProInfo.class);
        condition.startWith(ProInfo.InnerColumn.kjsj, date);
        Integer kj = proInfoService.countByCondition(condition);
        // 今日参与用户数
        int cyyh = infoMapper.countCyyhToday(date);
        Map<String, Integer> map = new HashMap<>();
        map.put("sj", sj);
        map.put("kj", kj);
        map.put("cyyh",cyyh);
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<List<String>> rechargeWater(String day) {
        // 不传默认7天
        if(StringUtils.isBlank(day)){
            day = "7";
        }
        DateTime now = DateTime.now();
        List<String> list = new ArrayList<>();
        int num = Integer.parseInt(day);
        for (int i = num-1; i >= 0; i--) {
            String s = now.minusDays(i).toString("yyyy-MM-dd");
            long sumAmount = rechargeMapper.sumAmount(s);
            String data = s + "|" + sumAmount;
            list.add(data);
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<List<String>> exchargeWater(String day) {
        if(StringUtils.isBlank(day)){
            day = "7";
        }
        DateTime now = DateTime.now();
        List<String> list = new ArrayList<>();
        int num = Integer.parseInt(day);
        for (int i = num-1; i >= 0; i--) {
            String s = now.minusDays(i).toString("yyyy-MM-dd");
            long sumAmount = exchangeMapper.sumAmount(s);
            String data = s + "|" + sumAmount;
            list.add(data);
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<List<String>> paymentWater(String day) {
        if(StringUtils.isBlank(day)){
            day = "7";
        }
        DateTime now = DateTime.now();
        List<String> list = new ArrayList<>();
        int num = Integer.parseInt(day);
        for (int i = num-1; i >= 0; i--) {
            String s = now.minusDays(i).toString("yyyy-MM-dd");
            long amount = rechargeMapper.sumAmount(s);
            long sumAmount = exchangeMapper.sumAmount(s);

            String data = s + "|" + amount + "|" + sumAmount;
            list.add(data);
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<Map<String, Long>> rechargeNum() {
        // 查询今日充值次数统计
        String today = DateTime.now().toString("yyyy-MM-dd");
        // 今日累计充值一次用户
        long one = rechargeMapper.sumreone(today);
        // 今日累计充值俩次的用户
        long two = rechargeMapper.sumretwo(today);
        // 今日累计充值三次的用户
        long more = rechargeMapper.sumremore(today);

        // 全部统计
        // 充值一次的统计
        long allone = rechargeMapper.sumAllone();
        // 充值俩次的用户数
        long alltwo = rechargeMapper.sumAlltwo();
        // 充值三次的用户数
        long allmore = rechargeMapper.sumAllmore();
        Map<String,Long> map = new HashMap<>();
        map.put("one",one);
        map.put("two", two);
        map.put("more", more);
        map.put("allone", allone);
        map.put("alltwo",alltwo);
        map.put("allmore",allmore);

        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<Map<String, Object>> rechargeChannel() {
        // 当天充值渠道统计
        String today = DateTime.now().toString("yyyy-MM-dd");
        List<Map<String,Long>> map = rechargeMapper.sumChannel(today);
        List<Map<String, Long>> longMap = rechargeMapper.sumAllChannel();
        Map<String,Object> res = new HashMap<>();
        res.put("today",map);
        res.put("all",longMap);
        return ApiResponse.success(res);
    }

    @Override
    public ApiResponse<List<Map<String, Long>>> rechargeTrend() {
        // 按月份
        String year = DateTime.now().toString("yyyy");
        List<Map<String, Long>> list = new ArrayList<>();
        for(int i = 1; i<= 12; i++){
            String mon;
            if(i< 10){
                mon = year + "-0" + i;
            }else{
                mon = year + "-" + i;
            }
            // 今日累计充值一次用户
            long one = rechargeMapper.sumreone(mon);
            // 今日累计充值俩次的用户
            long two = rechargeMapper.sumretwo(mon);
            // 今日累计充值三次的用户
            long more = rechargeMapper.sumremore(mon);
            Map<String, Long> map = new HashMap<>();
            map.put("one", one);
            map.put("two", two);
            map.put("more", more);
            list.add(map);
        }
        return ApiResponse.success(list);
    }
}
