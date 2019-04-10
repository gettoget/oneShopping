package com.ldz.biz.mapper;

import com.ldz.biz.model.OrderList;
import com.ldz.util.mapperprovider.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface OrderListMapper extends Mapper<OrderList> , InsertListMapper<OrderList> {
}