package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ExchangeService;
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
}
