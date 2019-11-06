package com.ldz.biz.service;

import com.ldz.biz.model.Bank;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BankService extends BaseService<Bank, String> {
    ApiResponse<Bank> getBankNo(String type);
}
