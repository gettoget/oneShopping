package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.AwardMapper;
import com.ldz.biz.model.Award;
import com.ldz.biz.service.AwardService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

public class AwardServiceImpl extends BaseServiceImpl<Award,String> implements AwardService {

    @Autowired
    private AwardMapper mapper;

    @Override
    protected Mapper<Award> getBaseMapper() {
        return mapper;
    }
}
