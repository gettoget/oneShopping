package com.ldz.biz.mapper;

import com.ldz.biz.model.OrderList;
import com.ldz.util.mapperprovider.InsertListMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderListMapper extends Mapper<OrderList> , InsertListMapper<OrderList> {


    @Select(" <script> select order_id orderId, num from order_list where order_id in <foreach collection= 'orderIds' index='index' item='item' open= '(' separator=',' close=')'>#{item}</foreach> </script>")
    List<OrderList> getList(@Param("orderIds") List<String> orderIds);
}