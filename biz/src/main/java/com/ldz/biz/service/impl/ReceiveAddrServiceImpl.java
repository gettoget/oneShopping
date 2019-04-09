package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ReceiveAddrService;
import com.ldz.biz.mapper.ReceiveAddrMapper;
import com.ldz.biz.model.ReceiveAddr;

@Service
public class ReceiveAddrServiceImpl extends BaseServiceImpl<ReceiveAddr, String> implements ReceiveAddrService {

	@Autowired
	private ReceiveAddrMapper baseMapper;
	
	@Override
	protected Mapper<ReceiveAddr> getBaseMapper() {
		return baseMapper;
	}
}