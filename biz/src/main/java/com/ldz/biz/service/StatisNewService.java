package com.ldz.biz.service;

import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface StatisNewService {


    ApiResponse<Map<String, Object>> statisNewRecharge(String time);

    ApiResponse<Map<String, Object>> statisNewCharge();

    ApiResponse<List<String>> statisNewWater(String day);

    ApiResponse<List<String>> statisNewUser(String day);

    ApiResponse<List<String>> statisCzqd(String time);

    ApiResponse<Map<String, String>> statisXzYh();

    ApiResponse<Map<String, String>> statisAllQd(String day);

    ApiResponse<Map<String, String>> statisQdJe();

    ApiResponse<Object> spgm();

    ApiResponse<Object> dtLlGm(String time, String id);

    ApiResponse<Object> dgkj(String time, String proName);
}
