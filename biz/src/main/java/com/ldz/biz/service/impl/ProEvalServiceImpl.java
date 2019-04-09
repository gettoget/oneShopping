package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProEvalService;
import com.ldz.biz.mapper.ProEvalMapper;
import com.ldz.biz.model.ProEval;

@Service
public class ProEvalServiceImpl extends BaseServiceImpl<ProEval, String> implements ProEvalService {

	@Autowired
	private ProEvalMapper baseMapper;
	
	@Override
	protected Mapper<ProEval> getBaseMapper() {
		return baseMapper;
	}
}