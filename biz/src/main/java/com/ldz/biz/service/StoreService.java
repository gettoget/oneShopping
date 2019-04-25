package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Store;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface StoreService extends BaseService<Store, String> {

    ApiResponse<String> saveEntity(Store entity);

    PageResponse<Store> getNewPager(Page<Store> page);

    ApiResponse<String> updateStoreCancel(String id);
}