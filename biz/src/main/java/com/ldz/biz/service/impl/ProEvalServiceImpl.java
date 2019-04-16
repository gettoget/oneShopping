package com.ldz.biz.service.impl;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
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
		entity.setUserId(userId);
		entity.setUserName(user.getUserName());
		entity.setId(genId());
		return null;
	}
}