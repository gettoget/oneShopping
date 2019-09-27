package com.ldz.biz.service;

import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface StatisService {
    ApiResponse<Map<String, Integer>> statisUser();

    ApiResponse<Map<String, Integer>> statisRecharge();

    ApiResponse<Map<String, Integer>> statisCharge();

    ApiResponse<Map<String, Integer>> statisPro();

    ApiResponse<List<String>> rechargeWater(String day);

    ApiResponse<List<String>> exchargeWater(String day);

    ApiResponse<List<String>> paymentWater(String day);

    ApiResponse<Map<String, Long>> rechargeNum();

    ApiResponse<Map<String, Object>> rechargeChannel();

    ApiResponse<List<Map<String, Long>>> rechargeTrend();

    ApiResponse<Map<String, String>> statisXf(String time);

    ApiResponse<List<String>> statisXfje(String day);

    ApiResponse<Map<String, String>> statisCzqd(String time);

    ApiResponse<List<String>> statisCzjb(String time);

    ApiResponse<List<String>> statisLastSeven(String day);

    ApiResponse<List<String>> statisCz(String day);

    ApiResponse<List<String>> statisCzsb(String day);

    ApiResponse<List<String>> statisNewUser(String day);

    ApiResponse<Map<String, Long>> rechargeNumFail();
}
