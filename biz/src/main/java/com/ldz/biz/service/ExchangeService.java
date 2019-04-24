package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Exchange;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.PageResponse;

public interface ExchangeService extends BaseService<Exchange, String> {

    PageResponse<Exchange> getNewPager(Page<Exchange> page);
}