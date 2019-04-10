package com.ldz.biz.service;

import com.ldz.biz.model.ProInfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ProInfoService extends BaseService<ProInfo, String> {

    ApiResponse<String> saveOne(String id);
}