package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ReceiveAddrMapper;
import com.ldz.biz.model.ReceiveAddr;
import com.ldz.biz.service.ReceiveAddrService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ReceiveAddrServiceImpl extends BaseServiceImpl<ReceiveAddr, String> implements ReceiveAddrService {

    @Autowired
    private ReceiveAddrMapper baseMapper;

    @Override
    protected Mapper<ReceiveAddr> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public ApiResponse<String> saveEntity(ReceiveAddr entity) {
        RuntimeCheck.ifBlank(entity.getAddress(), MessageUtils.get("addr.isBlank"));
        RuntimeCheck.ifBlank(entity.getRecName(), MessageUtils.get("addr.nameIsBlank"));
        RuntimeCheck.ifBlank(entity.getRecPhone(), MessageUtils.get("addr.phoneIsBlank"));

        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setUserId(userId);
        save(entity);
        return ApiResponse.success();
    }

    @Override
    public PageResponse<ReceiveAddr> getNewPager(Page<ReceiveAddr> page) {
        String userId = getAttributeAsString("userId");
        PageResponse<ReceiveAddr> res = new PageResponse<>();
        if (StringUtils.isNotBlank(userId)) {
            LimitedCondition condition = getQueryCondition();
            condition.eq(ReceiveAddr.InnerColumn.userId, userId);
            PageInfo<ReceiveAddr> info = findPage(page, condition);
            res.setPageNum(page.getPageNum());
            res.setList(info.getList());
            res.setPageSize(page.getPageSize());
            res.setTotal(info.getTotal());
        }

        return res;
    }

    @Override
    public ApiResponse<String> removeEntity(String id) {
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        remove(id);
        return ApiResponse.deleteSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ReceiveAddr entity) {
        String id = entity.getId();
        RuntimeCheck.ifBlank(id, MessageUtils.get("addr.isnull"));
        ReceiveAddr addr = findById(id);
        addr.setAddress(entity.getAddress());
        addr.setRecName(entity.getRecName());
        addr.setRecPhone(entity.getRecPhone());
        update(addr);
        return ApiResponse.updateSuccess();
    }


}