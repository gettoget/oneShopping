package com.ldz.biz.service;

import com.ldz.biz.model.Award;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface AwardService extends BaseService<Award,String> {

    ApiResponse<String> getAwardRecord();

    ApiResponse<String> saveLotty(String userId);
}
