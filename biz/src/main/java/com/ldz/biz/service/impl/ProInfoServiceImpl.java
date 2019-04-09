package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.ProInfo;

@Service
public class ProInfoServiceImpl extends BaseServiceImpl<ProInfo, String> implements ProInfoService {

	@Autowired
	private ProInfoMapper baseMapper;
	
	@Override
	protected Mapper<ProInfo> getBaseMapper() {
		return baseMapper;
	}
}