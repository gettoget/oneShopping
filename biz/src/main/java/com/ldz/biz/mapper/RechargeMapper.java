package com.ldz.biz.mapper;

import com.ldz.biz.model.Recharge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RechargeMapper extends Mapper<Recharge> {

    @Select(" SELECT * from  ((select IFNULL(SUM(CAST(amonut as UNSIGNED)),0) today from recharge where cjsj LIKE '${today}%'  ) a , (SELECT IFNULL(SUM(CAST(amonut as UNSIGNED)),0) mon from recharge where cjsj like '${mon}%' ) b , (SELECT IFNULL(SUM(CAST(amonut as UNSIGNED)),0) total from recharge ) c )")
    Map<String,Integer>  statisRecharge(@Param("today") String today,@Param("mon") String mon);

    @Select(" select IFNULL(SUM(CAST(amonut as UNSIGNED)),0) from recharge where cjsj like '${today}%'")
    long sumAmount(@Param("today") String time);

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null GROUP BY user_id HAVING c = 1 ) a")
    long sumreone(@Param("time")String time);

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null GROUP BY user_id HAVING c = 2 ) a")
    long sumretwo(@Param("time")String time);

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null GROUP BY user_id HAVING c > 2 ) a")
    long sumremore(@Param("time")String time);
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null GROUP BY user_id HAVING c =1 ) a")
    long sumAllone();
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null GROUP BY user_id HAVING c =2 ) a")
    long sumAlltwo();
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null GROUP BY user_id HAVING c > 2 ) a")
    long sumAllmore();

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null and cjsj like '${time}%' GROUP BY czqd; ")
    List<Map<String, Long>> sumChannel(@Param("time") String today);

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null GROUP BY czqd; ")
    List<Map<String, Long>> sumAllChannel();

    @Select(" select * from recharge where year like '${year}%'")
    List<Recharge> getYearRecharge(@Param("year") String year);
}