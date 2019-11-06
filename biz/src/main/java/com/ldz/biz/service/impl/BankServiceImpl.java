package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.BankMapper;
import com.ldz.biz.model.Bank;
import com.ldz.biz.service.BankService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
public class BankServiceImpl extends BaseServiceImpl<Bank,String> implements BankService {

    @Autowired
    private BankMapper mapper;
    @Value("${czqr}")
    private int czqr;

    @Override
    protected Mapper<Bank> getBaseMapper() {
        return mapper;
    }

    @Override
    public ApiResponse<Bank> getBankNo(String type) {
        if(StringUtils.isBlank(type)){
            type = null;
        }
        Bank bank = mapper.getByType(type, DateTime.now().toString("yyyy-MM-dd"),czqr);
        return ApiResponse.success(bank);
    }
}
