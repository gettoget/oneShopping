package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.mapper.ProBaseinfoMapper;
import com.ldz.biz.model.ProBaseinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProBaseinfoServiceImpl extends BaseServiceImpl<ProBaseinfo, String> implements ProBaseinfoService {

	@Autowired
	private ProBaseinfoMapper baseMapper;

	@Value("${filePath}")
	private String filePath;

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

    @Override
	public void afterPager(PageInfo<ProBaseinfo> result){
		List<ProBaseinfo> list = result.getList();
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for (ProBaseinfo proBaseinfo : list) {
			setImgUrl(proBaseinfo);
		}
	}

	@Override
	public void afterQuery(List<ProBaseinfo> result) {

		for (ProBaseinfo proBaseinfo : result) {
			setImgUrl(proBaseinfo);
		}
	}


	private void setImgUrl(ProBaseinfo info){
		List<String> imgUrls = new ArrayList<>();
		for (String s : info.getUrls().split(",")) {
			imgUrls.add(filePath + s);
		}
		info.setImgUrls(imgUrls);
		List<String> coverUrls = new ArrayList<>();
		for (String s : info.getCoverUrl().split(",")) {
			coverUrls.add(filePath + s);
		}
		info.setCoverUrls(coverUrls);
		List<String> refUrls = new ArrayList<>();
		for (String s : info.getRefUrl().split(",")) {
			refUrls.add(filePath + s);
		}
		info.setRefUrls(refUrls);
	}

}