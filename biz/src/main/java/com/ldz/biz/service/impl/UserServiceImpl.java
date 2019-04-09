package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.UserService;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.User;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Autowired
	private UserMapper baseMapper;
	
	@Override
	protected Mapper<User> getBaseMapper() {
		return baseMapper;
	}
}