package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.AwardMapper;
import com.ldz.biz.model.Award;
import com.ldz.biz.service.AwardService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class AwardServiceImpl extends BaseServiceImpl<Award,String> implements AwardService {

    @Autowired
    private AwardMapper mapper;


    @Override
    protected Mapper<Award> getBaseMapper() {
        return mapper;
    }

    @Override
    public ApiResponse<String> getAwardRecord() {

        String userId = getHeader("userId");
        SimpleCondition condition = new SimpleCondition(Award.class);
        condition.startWith(Award.InnerColumn.cjsj, DateTime.now().toString("yyyy-MM-dd"));
        condition.eq(Award.InnerColumn.userId, userId);
        List<Award> awards = findByCondition(condition);
        return ApiResponse.success(awards.size()+"");
    }
    @Override
    public ApiResponse<String> validAndSave(Award entity){
        String userId = getHeader("userId");
        if(StringUtils.isNotBlank(userId)){
            entity.setUserId(userId);
        }
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setStatus("0");
        save(entity);
       return ApiResponse.success();
    }


}
