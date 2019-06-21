package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.PopularimgsMapper;
import com.ldz.biz.model.Popularimgs;
import com.ldz.biz.service.PopularimgsService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Arrays;
import java.util.List;

@Service
public class PopularimgsServiceImpl extends BaseServiceImpl<Popularimgs, String> implements PopularimgsService {

	@Autowired
	private PopularimgsMapper baseMapper;


	@Value("${filePath}")
	private String filePath;
	
	@Override
	protected Mapper<Popularimgs> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> saveEntity(Popularimgs entity) {

		RuntimeCheck.ifBlank(entity.getImgUrl(), MessageUtils.get("img.urlBlank"));
		RuntimeCheck.ifBlank(entity.getImgLx(), MessageUtils.get("img.lxError"));
		RuntimeCheck.ifFalse(Arrays.asList("1","2").contains(entity.getImgLx()), MessageUtils.get("img.lxWarn"));
		RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("img.proIsNull"));
		entity.setId(genId());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setZt(StringUtils.isBlank(entity.getZt())? "0":entity.getZt());
		entity.setFwurl(filePath + entity.getImgUrl());
		save(entity);
		return ApiResponse.saveSuccess();
    }


    public void afterPager(PageInfo<Popularimgs> result){
		List<Popularimgs> list = result.getList();
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		list.forEach(popularimgs -> {
			if(!StringUtils.startsWith(popularimgs.getImgUrl(),"http")){
				popularimgs.setImgUrl(filePath + popularimgs.getImgUrl());
			}
		});
	}



}