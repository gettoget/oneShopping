package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.OrderListMapper;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.service.OrderListService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class OrderListServiceImpl extends BaseServiceImpl<OrderList, String> implements OrderListService {

	@Autowired
	private OrderListMapper baseMapper;
	
	@Override
	protected Mapper<OrderList> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public void saveList(List<OrderList> list) {
		baseMapper.insertList(list);
    }
}