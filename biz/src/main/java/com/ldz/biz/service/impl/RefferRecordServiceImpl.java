package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.RefferRecordService;
import com.ldz.biz.mapper.RefferRecordMapper;
import com.ldz.biz.model.RefferRecord;

@Service
public class RefferRecordServiceImpl extends BaseServiceImpl<RefferRecord, String> implements RefferRecordService {

	@Autowired
	private RefferRecordMapper baseMapper;
	
	@Override
	protected Mapper<RefferRecord> getBaseMapper() {
		return baseMapper;
	}
}