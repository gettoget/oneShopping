package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ReceiveAddr;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface ReceiveAddrService extends BaseService<ReceiveAddr, String> {

    ApiResponse<String> saveEntity(ReceiveAddr entity);

    PageResponse<ReceiveAddr> getNewPager(Page<ReceiveAddr> page);

    ApiResponse<String> removeEntity(String id);

    ApiResponse<String> updateEntity(ReceiveAddr entity);
}