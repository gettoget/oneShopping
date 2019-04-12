package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
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

    @Override
    public ApiResponse<String> saveEntity(ProBaseinfo entity) {
		RuntimeCheck.ifBlank(entity.getProName(), MessageUtils.get("pro.nameBlank"));
		RuntimeCheck.ifBlank(entity.getProType(), MessageUtils.get("pro.typeBlank"));
		RuntimeCheck.ifBlank(entity.getUrls(), MessageUtils.get("pro.imgBlank"));
		RuntimeCheck.ifBlank(entity.getProPrice(), MessageUtils.get("pro.priceBlank"));
		RuntimeCheck.ifBlank(entity.getProStore(), MessageUtils.get("pro.storeBlank"));
		RuntimeCheck.ifBlank(entity.getrType(), MessageUtils.get("pro.rTypeBlank"));
		RuntimeCheck.ifBlank(entity.getCoverUrl(), MessageUtils.get("pro.coverBlank"));

		entity.setId(genId());
		entity.setCjsj(DateUtils.getNowTime());
		save(entity);

		return ApiResponse.saveSuccess();
    }
}