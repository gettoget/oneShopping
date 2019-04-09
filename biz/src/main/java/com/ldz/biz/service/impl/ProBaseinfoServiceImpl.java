package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.mapper.ProBaseinfoMapper;
import com.ldz.biz.model.ProBaseinfo;

@Service
public class ProBaseinfoServiceImpl extends BaseServiceImpl<ProBaseinfo, String> implements ProBaseinfoService {

	@Autowired
	private ProBaseinfoMapper baseMapper;
	
	@Override
	protected Mapper<ProBaseinfo> getBaseMapper() {
		return baseMapper;
	}
}