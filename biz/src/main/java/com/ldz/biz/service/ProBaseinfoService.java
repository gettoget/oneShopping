package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.util.bean.PageResponse;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

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

}