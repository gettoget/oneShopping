package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.WinRecordService;
import com.ldz.biz.mapper.WinRecordMapper;
import com.ldz.biz.model.WinRecord;

@Service
public class WinRecordServiceImpl extends BaseServiceImpl<WinRecord, String> implements WinRecordService {

	@Autowired
	private WinRecordMapper baseMapper;
	
	@Override
	protected Mapper<WinRecord> getBaseMapper() {
		return baseMapper;
	}
}