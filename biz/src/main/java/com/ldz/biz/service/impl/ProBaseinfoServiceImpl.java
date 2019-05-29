package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.WinRecord;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.WinRecordService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProBaseinfoServiceImpl extends BaseServiceImpl<ProBaseinfo, String> implements ProBaseinfoService {

	@Autowired
	private ProBaseinfoMapper baseMapper;

	@Autowired
	private WinRecordService recordService;

	@Value("${filePath}")
	private String filePath;

	@Autowired
	private ProInfoService infoService;

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
		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(entity.getId());
		return res;
    }

    @Override
    public ApiResponse<List<WinRecord>> getWinRecord(String id) {
        RuntimeCheck.ifBlank(id , MessageUtils.get("pro.baseIdIsBlank"));
		// 找到前五十条已中奖的商品 id
		List<String> proIds = baseMapper.getFifProId(id);
		List<WinRecord> records = recordService.findIn(WinRecord.InnerColumn.proId, proIds);
		if(CollectionUtils.isNotEmpty(records)){
			records =  records.stream().sorted(Comparator.comparing(WinRecord::getCjsj).reversed()).collect(Collectors.toList());
		}
		return ApiResponse.success(records);
    }

    @Override
	public void afterPager(PageInfo<ProBaseinfo> result){
		List<ProBaseinfo> list = result.getList();
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		SimpleCondition condition;
		for (ProBaseinfo proBaseinfo : list) {
			condition = new SimpleCondition(ProInfo.class);
			condition.eq(ProInfo.InnerColumn.proBaseid, proBaseinfo.getId());
			condition.eq(ProInfo.InnerColumn.proZt, "4");
			Integer count = infoService.countByCondition(condition);
			proBaseinfo.setKjs(count);
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