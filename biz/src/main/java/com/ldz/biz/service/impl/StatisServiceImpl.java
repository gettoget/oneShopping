package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.Exchange;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.StatisService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Map<String,String>> todayList = new ArrayList<>();
        List<Map<String,String>> allList = new ArrayList<>();
        Map<String,String> map = rechargeMapper.sumChannelOne(today);
        if(map == null){
            map = new HashedMap();
            map.put("czqd","1");
            map.put("cz","0");
        }
        Map<String, String> channelTwo = rechargeMapper.sumChannelTwo(today);
        if(channelTwo == null){
            channelTwo = new HashedMap();
            channelTwo.put("czqd","2");
            channelTwo.put("cz","0");
        }
        todayList.add(map);
        todayList.add(channelTwo);


        Map<String,String> allMap = rechargeMapper.sumAllChannelOne();
        if(map == null){
            allMap = new HashedMap();
            allMap.put("czqd","1");
            allMap.put("cz","0");
        }
        Map<String, String> allChannelTwo = rechargeMapper.sumAllChannelTwo();
        if(allChannelTwo == null){
            allChannelTwo = new HashedMap();
            allChannelTwo.put("czqd","2");
            allChannelTwo.put("cz","0");
        }
        allList.add(allMap);
        allList.add(allChannelTwo);

        Map<String,Object> res = new HashMap<>();
        res.put("today",todayList);
        res.put("all",allList);
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

    @Override
    public ApiResponse<Map<String, String>> statisXf(String time) {
        // 查询消费走势 ， 分为 1 ， 2 ， 和多次
        Map<String,String> result = new HashMap<>();
        if(StringUtils.isBlank(time)){
            time = DateTime.now().toString("yyyy-MM-dd");
        }
        List<Map<String, Long>> map = exchangeMapper.statisXf(time);
        Map<Long, List<Map<String, Long>>> co = map.stream().collect(Collectors.groupingBy(m -> m.get("co")));
        for(int i = 0 ; i< 3 ; i++){
            Long key = Long.valueOf((i + 1));
            if(co.containsKey(key)){
                List<Map<String, Long>> list = co.get(key);
                Long j = Long.valueOf(list.get(0).get("j"));
                result.put(key+"",j+"");
            }else{
                result.put(key+"","0");
            }
        }

        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisXfje(String day) {
        if(StringUtils.isBlank(day)){
            day = "30";
        }
        List<String> result = new ArrayList<>();

        List<String> times = new ArrayList<>();

        int parseDay = Integer.parseInt(day);
        DateTime now = DateTime.now();
        DateTime dateTime = now.minusDays(parseDay-1);
        for (int i = 0; i < parseDay; i++) {
            times.add(dateTime.plusDays(i).toString("yyyy-MM-dd"));
        }

        String start = dateTime.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");
        List<Exchange> list = exchangeMapper.statisXfje(start, end);
        Map<String, String> map = list.stream().collect(Collectors.toMap(Exchange::getTime, p -> p.getJe()));
        for (String time : times) {
            String s = map.get(time);
            if(StringUtils.isBlank(s)){
                s = "0";
            }
            result.add(time + "," + s);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<Map<String, String>> statisCzqd(String time) {
        if(StringUtils.isBlank(time)){
            time = DateTime.now().toString("yyyy-MM-dd");
        }
        Map<String,String> m = new HashMap<>();
        List<Recharge> recharges = rechargeMapper.statisCzqd(time);
        Map<String, Recharge> map = recharges.stream().collect(Collectors.toMap(Recharge::getCzqd, p -> p));
        for(int i = 1 ; i <= 2 ;  i++){
            String key =  i + "";
            if(map.containsKey(key)){
                Recharge recharge = map.get(key);
                m.put(key, recharge.getCzjb() + "," + recharge.getBz1());
            }else{
                m.put(key, "0,0");
            }
        }

        return ApiResponse.success(m);
    }

    @Override
    public ApiResponse<List<String>> statisCzjb(String time) {
        // 查询消费走势 ， 分为 1 ， 2 ， 和多次
        List<String> result = new ArrayList<>();
        if(StringUtils.isBlank(time)){
            time = DateTime.now().toString("yyyy-MM-dd");
        }
        List<Recharge> map = rechargeMapper.statisCzjb(time);

        Map<String, Recharge> collect = map.stream().collect(Collectors.toMap(Recharge::getBz1, p -> p));
        for(int i = 1 ; i<= 3 ; i++){
            String key = i+"";
            if(collect.containsKey(key)){
                Recharge j = collect.get(key);
                result.add( j.getCzjb() + "," + j.getBz2());
            }else{
                result.add( "0,0");
            }
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisLastSeven(String day) {
        if(StringUtils.isBlank(day)){
            day = "7";
        }
        List<String> result = new ArrayList<>();

        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        String start = minusDays.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");
        // 支付通道
        List<Recharge> qdRecord = rechargeMapper.getQdRecord("1", start, end);
        Map<String, String> collect = qdRecord.stream().collect(Collectors.toMap(Recharge::getCjsj, p -> p.getCzjb()));
        // 积分通道
        List<Recharge> record = rechargeMapper.getQdRecord("2", start, end);
        Map<String, String> stringMap = record.stream().collect(Collectors.toMap(Recharge::getCjsj, p -> p.getCzjb()));
        for (int i = 0; i < anInt; i++) {
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            String value = key;
            if(collect.containsKey(key)){
                value = value + "," +  collect.get(key);
            }else{
                value = value + "," + "0";
            }
            if(stringMap.containsKey(key)){
                value = value + "," + stringMap.get(key);
            }else{
                value = value + "," + "0";
            }
            result.add(value);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisCz(String day) {
        if(StringUtils.isBlank(day)){
            day = "30";
        }
        List<String> result = new ArrayList<>();
        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        String start = minusDays.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");

        List<Recharge> recharges = rechargeMapper.getCz(start, end);
        Map<String, String> map = recharges.stream().collect(Collectors.toMap(Recharge::getCjsj, p -> p.getCzjb()));
        for (int i = 0; i < anInt ; i++){
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            String value = key;
            if(map.containsKey(key)){
                value += "," + map.get(key);
            }else{
                value += ",0";
            }
            result.add(value);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisCzsb(String day) {
        if(StringUtils.isBlank(day)){
            day = "30";
        }
        List<String> result = new ArrayList<>();
        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        String start = minusDays.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");

        List<Recharge> recharges = rechargeMapper.getCzsb(start, end);
        Map<String, String> map = recharges.stream().collect(Collectors.toMap(Recharge::getCjsj, p -> p.getCzjb()));
        for (int i = 0; i < anInt ; i++){
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            String value = key;
            if(map.containsKey(key)){
                value += "," + map.get(key);
            }else{
                value += ",0";
            }
            result.add(value);
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse<List<String>> statisNewUser(String day) {
        if(StringUtils.isBlank(day)){
            day = "30";
        }
        List<String> result = new ArrayList<>();
        DateTime now = DateTime.now();
        int anInt = Integer.parseInt(day);
        DateTime minusDays = now.minusDays(anInt - 1);
        String start = minusDays.toString("yyyy-MM-dd");
        String end = now.toString("yyyy-MM-dd");
        List<User> list = userMapper.countUsers(start, end);
        Map<String, String> map = list.stream().collect(Collectors.toMap(User::getCjsj, p -> p.getBz1()));
        for(int i = 0 ; i < anInt ; i++){
            String key = minusDays.plusDays(i).toString("yyyy-MM-dd");
            String value = key;
            if(map.containsKey(key)){
                value += ","+map.get(key);
            }else{
                value += ",0";
            }
            result.add(value);
        }

        return ApiResponse.success(result);
    }
}
