package com.ldz.biz.service;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.WinRecord;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

import java.util.List;

/**
 * @author slu
 */
public interface ProInfoService extends BaseService<ProInfo, String> {
    /**
     * 数据保存
     * @param id
     * @return
     */
    ApiResponse<String> saveOne(String id);

    /**
     * 商品最近的一次中奖记录
     * @param id
     * @return
     */
    ApiResponse<ProInfo> getLatestPerson(String id);

    /**
     * 获取抢购商品的实时信息
     * @param id
     * @return
     */
    ApiResponse<ProInfo> getProInfo(String id);


    ApiResponse<String> getUserInfo(String id, int pageNum, int pageSize);

    String getBaseId(String id);

    PageResponse<ProInfo> getRePager(int pageNum, int pageSize);

    ApiResponse<WinRecord> getLastestRecord(String id);

    ApiResponse<List<String>> getMyNums(String id);

    PageResponse<WinRecord> winrecoeds(String id, int pageNum , int pageSize);
}