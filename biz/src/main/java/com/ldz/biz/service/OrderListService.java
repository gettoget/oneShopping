package com.ldz.biz.service;

import com.ldz.biz.model.OrderList;
import com.ldz.sys.base.BaseService;

import java.util.List;

public interface OrderListService extends BaseService<OrderList, String> {

   void saveList(List<OrderList> list);

}