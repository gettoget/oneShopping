package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import com.ldz.biz.service.StoreService;
import com.ldz.biz.mapper.StoreMapper;
import com.ldz.biz.model.Store;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, String> implements StoreService {

    @Autowired
    private StoreMapper baseMapper;
    @Autowired
    private ProBaseinfoService proBaseinfoService;

    @Override
    protected Mapper<Store> getBaseMapper() {
        return baseMapper;
    }

}