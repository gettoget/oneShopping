package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.PopularimgsService;
import com.ldz.biz.mapper.PopularimgsMapper;
import com.ldz.biz.model.Popularimgs;

@Service
public class PopularimgsServiceImpl extends BaseServiceImpl<Popularimgs, String> implements PopularimgsService {

	@Autowired
	private PopularimgsMapper baseMapper;
	
	@Override
	protected Mapper<Popularimgs> getBaseMapper() {
		return baseMapper;
	}
}