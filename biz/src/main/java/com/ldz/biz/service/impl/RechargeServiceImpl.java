package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.RechargeService;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.Recharge;

@Service
public class RechargeServiceImpl extends BaseServiceImpl<Recharge, String> implements RechargeService {

	@Autowired
	private RechargeMapper baseMapper;
	
	@Override
	protected Mapper<Recharge> getBaseMapper() {
		return baseMapper;
	}
}