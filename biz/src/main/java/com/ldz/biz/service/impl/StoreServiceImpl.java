package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import com.ldz.biz.service.StoreService;
import com.ldz.biz.mapper.StoreMapper;
import com.ldz.biz.model.Store;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, String> implements StoreService {

    @Autowired
    private StoreMapper baseMapper;

    @Override
    protected Mapper<Store> getBaseMapper() {
        return baseMapper;
    }
}