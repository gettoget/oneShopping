package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.model.Exchange;
import com.ldz.biz.service.ExchangeService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ExchangeServiceImpl extends BaseServiceImpl<Exchange, String> implements ExchangeService {

	@Autowired
	private ExchangeMapper baseMapper;
	
	@Override
	protected Mapper<Exchange> getBaseMapper() {
		return baseMapper;
	}
}