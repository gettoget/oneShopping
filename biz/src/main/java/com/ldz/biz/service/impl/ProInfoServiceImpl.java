package com.ldz.biz.service.impl;

import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
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

	@Autowired
	private ProBaseinfoService proBaseinfoService;

	@Override
	protected Mapper<ProInfo> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> saveOne(String id) {
		ProBaseinfo baseinfo = proBaseinfoService.findById(id);
		int storeNum = Integer.parseInt(baseinfo.getProStore());
		if(storeNum <= 0 ){
			return ApiResponse.fail(MessageUtils.get("pro.storeIsNull"));
		}
		ProInfo  proInfo = new ProInfo();
		proInfo.setId(genId());
		proInfo.setCjsj(DateUtils.getNowTime());
		proInfo.setCyyhs("0");
		proInfo.setGxsj(proInfo.getCjsj());
		proInfo.setProBaseid(id);
		proInfo.setProName(baseinfo.getProName());
		proInfo.setProPrice(baseinfo.getProPrice());
		proInfo.setProSign(baseinfo.getProSign());
		proInfo.setProStore("1");
		proInfo.setProType(baseinfo.getProType());
		proInfo.setProZt("1");
		proInfo.setRePrice(baseinfo.getProPrice());
		proInfo.setrType(baseinfo.getrType());
		proInfo.setUrls(baseinfo.getUrls());
		save(proInfo);

		baseinfo.setProStore(storeNum -1 + "");
		proBaseinfoService.update(baseinfo);
		return ApiResponse.success(MessageUtils.get("pro.groundSuc"));
	}
}