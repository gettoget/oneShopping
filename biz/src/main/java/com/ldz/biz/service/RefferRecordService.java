package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.RefferRecord;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface RefferRecordService extends BaseService<RefferRecord, String> {

    ApiResponse<String> saveRecord(String refCode);

    PageResponse<RefferRecord> getNewPager(Page<RefferRecord> page);
}