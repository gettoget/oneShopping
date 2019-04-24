package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.dvcs.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.rmi.server.InactiveGroupException;
import tk.mybatis.mapper.common.Mapper;
import com.ldz.biz.service.StoreService;
import com.ldz.biz.mapper.StoreMapper;
import com.ldz.biz.model.Store;

import java.awt.*;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, String> implements StoreService {

    @Autowired
    private StoreMapper baseMapper;
    @Autowired
    private ProInfoService proInfoService;

    @Override
    protected Mapper<Store> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public ApiResponse<String> saveEntity(Store entity) {
        RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("store.proIsBlank"));
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        SimpleCondition condition = new SimpleCondition(Store.class);

        condition.eq(Store.InnerColumn.userId, userId);
        condition.eq(Store.InnerColumn.proId, entity.getProId());
        List<Store> list = findByCondition(condition);
        RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(list), MessageUtils.get("store.proIsStored"));
        entity.setCjsj(DateUtils.getNowTime());
        entity.setUserId(userId);
        ProInfo proInfo = proInfoService.findById(entity.getProId());
        entity.setProName(proInfo.getProName());
        entity.setId(genId());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public PageResponse<Store> getNewPager(Page<Store> page) {
        PageResponse<Store> res = new PageResponse<>();
        String userId = getAttributeAsString("userId");
        if(StringUtils.isNotBlank(userId)){
            LimitedCondition condition = getQueryCondition();
            condition.eq(Store.InnerColumn.userId, userId);
            PageInfo<Store> info = findPage(page, condition);
            if(CollectionUtils.isNotEmpty(info.getList())){
                List<String> proIds = info.getList().stream().map(Store::getProId).collect(Collectors.toList());
                List<ProInfo> infos = proInfoService.findByIds(proIds);
                Map<String, ProInfo> proInfoMap = infos.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));
                info.getList().stream().forEach(store -> store.setProInfo(proInfoMap.get(store.getProId())));
            }
            res.setTotal(info.getTotal());
            res.setList(info.getList());
            res.setPageSize(page.getPageSize());
            res.setPageNum(page.getPageNum());
        }

        return res;
    }

    @Override
    public ApiResponse<String> updateStoreCancel(String id) {
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        SimpleCondition condition = new SimpleCondition(Store.class);
        condition.eq(Store.InnerColumn.userId, userId);
        condition.eq(Store.InnerColumn.id, id);
        List<Store> stores = findByCondition(condition);
        if(CollectionUtils.isNotEmpty(stores)){
           remove(id);
        }

        return ApiResponse.deleteSuccess();
    }
}