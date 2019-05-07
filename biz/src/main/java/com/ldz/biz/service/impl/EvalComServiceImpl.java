package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.EvalComMapper;
import com.ldz.biz.model.EvalCom;
import com.ldz.biz.service.EvalComService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class EvalComServiceImpl extends BaseServiceImpl<EvalCom,String> implements EvalComService {

    @Autowired
    private EvalComMapper baseMapper;

    @Override
    protected Mapper<EvalCom> getBaseMapper() {
        return baseMapper;
    }
}
