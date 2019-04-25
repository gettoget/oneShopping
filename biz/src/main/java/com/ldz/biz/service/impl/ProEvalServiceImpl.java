package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProEvalService;
import com.ldz.biz.mapper.ProEvalMapper;
import com.ldz.biz.model.ProEval;

@Service
public class ProEvalServiceImpl extends BaseServiceImpl<ProEval, String> implements ProEvalService {

	@Autowired
	private ProEvalMapper baseMapper;

	@Autowired
	private UserService userService;

	@Override
	protected Mapper<ProEval> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> saveEntity(ProEval entity) {
		RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("eval.proBlank"));
		RuntimeCheck.ifBlank(entity.getContent(), MessageUtils.get("eval.contentBlank"));
		String userId = (String) getAttribute("userId");
		User user = userService.findById(userId);
		RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));

		entity.setUserId(userId);
		entity.setUserName(user.getUserName());
		entity.setId(genId());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public PageResponse<ProEval> getNewPager(Page<ProEval> page) {
		LimitedCondition condition = getQueryCondition();
		PageInfo<ProEval> info = findPage(page, condition);
		PageResponse<ProEval> res = new PageResponse<>();
		res.setList(info.getList());
		res.setPageNum(page.getPageNum());
		res.setPageSize(page.getPageSize());
		res.setTotal(info.getTotal());
		return res;
	}
}