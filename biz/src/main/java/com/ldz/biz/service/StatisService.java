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
}
