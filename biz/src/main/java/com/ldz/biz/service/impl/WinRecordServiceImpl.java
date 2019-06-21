package com.ldz.biz.service.impl;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.WinRecordService;
import com.ldz.biz.mapper.WinRecordMapper;
import com.ldz.biz.model.WinRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WinRecordServiceImpl extends BaseServiceImpl<WinRecord, String> implements WinRecordService {

	@Autowired
	private WinRecordMapper baseMapper;
	@Autowired
	private ProInfoService proInfoService;
	@Override
	protected Mapper<WinRecord> getBaseMapper() {
		return baseMapper;
	}
	@Override
	public void afterNewPager(List<WinRecord> records){
		if(CollectionUtils.isEmpty(records)){
			return;
		}
		List<String> collect = records.stream().map(WinRecord::getProId).collect(Collectors.toList());
		List<ProInfo> infos = proInfoService.findByIds(collect);
		Map<String, String> map = infos.stream().collect(Collectors.toMap(ProInfo::getId, p -> p.getProName()));
		records.forEach(winRecord -> winRecord.setProName(map.get(winRecord.getProId())));
	}

}