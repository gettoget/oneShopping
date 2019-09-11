package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.SharedMapper;
import com.ldz.biz.model.Shared;
import com.ldz.biz.service.SharedService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SharedServiceImpl extends BaseServiceImpl<Shared,String> implements SharedService {

    @Autowired
    private SharedMapper mapper;
    @Override
    protected Mapper<Shared> getBaseMapper() {
        return mapper;
    }

    @Override
    public ApiResponse<String> validAndSave(Shared entity) {
        String userId = getHeader("userId");
        entity.setCjsj(DateUtils.getNowTime());
        entity.setId(genId());
        entity.setUserId(userId);
        save(entity);
        return ApiResponse.success();
    }
}
