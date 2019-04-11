package com.ldz.biz.service;

import com.ldz.biz.model.Popularimgs;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

/**
 * @author slu
 */
public interface PopularimgsService extends BaseService<Popularimgs, String> {
    /**
     * 数据保存
     * @param entity
     * @return
     */
    ApiResponse<String> saveEntity(Popularimgs entity);
}