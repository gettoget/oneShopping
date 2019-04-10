package com.ldz.biz.service;

import com.ldz.biz.model.Popularimgs;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface PopularimgsService extends BaseService<Popularimgs, String> {

    ApiResponse<String> saveEntity(Popularimgs entity);
}