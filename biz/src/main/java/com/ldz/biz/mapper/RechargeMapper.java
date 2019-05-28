package com.ldz.biz.mapper;

import com.ldz.biz.model.Recharge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface RechargeMapper extends Mapper<Recharge> {

    @Select(" SELECT * from  ((select IFNULL(SUM(CAST(amonut as UNSIGNED)),0) today from recharge where cjsj LIKE '${today}%'  ) a , (SELECT IFNULL(SUM(CAST(amonut as UNSIGNED)),0) mon from recharge where cjsj like '${mon}%' ) b , (SELECT IFNULL(SUM(CAST(amonut as UNSIGNED)),0) total from recharge ) c )")
    Map<String,Integer>  statisRecharge(@Param("today") String today,@Param("mon") String mon);

    @Select(" select IFNULL(SUM(CAST(amonut as UNSIGNED)),0) from recharge where cjsj like '${today}%'")
    long sumAmount(@Param("today") String time);

}