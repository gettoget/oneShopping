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

    ApiResponse<Object> dgkj(String time, String proName, String orderBy, int pageNum, int pageSize);

    ApiResponse<Object> dgyh(String time);

    ApiResponse<Object> zhbh(String id, int pageNum, int pageSize);

    ApiResponse<Object> kj(String time, String end, String proName, String orderBy, int pageNum, int pageSize);

    ApiResponse<Object> yhgm(String time, String end , String name, String orderBy, int pageNum, int pageSize);

    ApiResponse<List<String>> getCyl(String day);

    ApiResponse<List<Integer>> getCzfb();

    ApiResponse<List<String>> getCzl(String day);

    ApiResponse<List<String>> getHyd(String day);

    ApiResponse<List<String>> getHyqd(String day);
}
