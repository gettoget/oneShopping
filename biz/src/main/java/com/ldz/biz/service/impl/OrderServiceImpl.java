package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.model.Order;
import com.ldz.biz.service.OrderService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

	@Autowired
	private OrderMapper baseMapper;
	
	@Override
	protected Mapper<Order> getBaseMapper() {
		return baseMapper;
	}
}