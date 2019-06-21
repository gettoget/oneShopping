package com.ldz.biz.mapper;

import com.ldz.biz.model.Exchange;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ExchangeMapper extends Mapper<Exchange> {

    @Select(" SELECT * from  ((select IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) today from exchange where xfsj LIKE '${today}%'  ) a , (SELECT IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) mon from exchange where xfsj like '${mon}%' ) b , (SELECT IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) total from exchange ) c ) ")
    Map<String,Integer> statisExCharge(@Param("today") String today,@Param("mon") String mon);

    @Select(" select IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) from exchange where xfsj like '${today}%'")
    long sumAmount(@Param("today") String s);
}