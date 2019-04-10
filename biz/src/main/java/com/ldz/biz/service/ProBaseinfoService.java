package com.ldz.biz.service;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ProBaseinfoService extends BaseService<ProBaseinfo, String> {

    ApiResponse<String> saveEntity(ProBaseinfo entity);
}