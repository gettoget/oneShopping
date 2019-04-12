package com.ldz.biz.service;

import com.ldz.biz.model.RefferRecord;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface RefferRecordService extends BaseService<RefferRecord, String> {

    ApiResponse<String> saveRecord(String refCode);
}