package com.ldz.biz.service;

import com.ldz.biz.model.WinRecord;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

/**
 * @author slu
 */
public interface ProBaseinfoService extends BaseService<ProBaseinfo, String> {
    /**
     * 数据保存
     * @param entity
     * @return
     */
    ApiResponse<String> saveEntity(ProBaseinfo entity);

    ApiResponse<List<WinRecord>> getWinRecord(String id);
}