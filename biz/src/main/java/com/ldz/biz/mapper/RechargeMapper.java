package com.ldz.biz.mapper;

import com.ldz.biz.model.*;
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

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null  and czqd = '1' and czzt='2' GROUP BY user_id HAVING c = 1 ) a")
    long sumreone(@Param("time")String time);

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null and czqd = '1' and czzt='2'  GROUP BY user_id HAVING c = 2 ) a")
    long sumretwo(@Param("time")String time);

    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where cjsj like '${time}%' and user_id is not null and czqd = '1' and czzt='2'   GROUP BY user_id HAVING c > 2 ) a")
    long sumremore(@Param("time")String time);
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null and  czqd = '1' and czzt='2'  GROUP BY user_id HAVING c =1 ) a")
    long sumAllone();
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null and czqd='1' and czzt='2'  GROUP BY user_id HAVING c =2 ) a")
    long sumAlltwo();
    @Select(" SELECT COUNT(c) count from (SELECT count(*) c from recharge where  user_id is not null and czqd = '1' and czzt='2'  GROUP BY user_id HAVING c > 2 ) a")
    long sumAllmore();

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null and cjsj like '${time}%' and czqd='1' GROUP BY czqd ")
    Map<String, String> sumChannelOne(@Param("time") String today);

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null and cjsj like '${time}%' and czqd='2' GROUP BY czqd ")
    Map<String, String> sumChannelTwo(@Param("time") String today);

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null and czqd='1' GROUP BY czqd; ")
    Map<String, String> sumAllChannelOne();

    @Select(" SELECT czqd ,count(recharge.czqd) cz from recharge where user_id is not null and czqd='2' GROUP BY czqd; ")
    Map<String, String> sumAllChannelTwo();

    @Select(" select * from recharge where year like '${year}%'")
    List<Recharge> getYearRecharge(@Param("year") String year);

    @Select(" select czqd ,convert( SUM(czjb), signed) czjb, COUNT(czqd) bz1 from recharge where cjsj like '%${time}%' GROUP BY czqd ;")
    List<Recharge> statisCzqd(@Param("time") String time);

    @Select("SELECT co bz1, convert(sum(a.jb) ,SIGNED) czjb , COUNT(DISTINCT user_id) bz2 from ( SELECT user_id, IF(count(user_id) >= 3,3,count(user_id)) co,sum(czjb) jb from recharge where cjsj like '%${time}%' and czqd = '1' group by user_id ) a GROUP BY co")
    List<Recharge> statisCzjb(@Param("time") String time);

    @Select(" SELECT SUBSTR(cjsj,1,10) cjsj,convert(sum(czjb),signed) czjb  from recharge WHERE  czqd = #{czqd} and SUBSTR(cjsj,1,10) >= #{start} and SUBSTR(cjsj,1,10) <= #{end}  GROUP BY SUBSTR(cjsj,1,10)  ")
    List<Recharge> getQdRecord(@Param("czqd") String czqd ,@Param("start") String start,@Param("end") String end);

    @Select("select SUBSTR(cjsj , 1 , 10) cjsj ,convert(sum(czjb),signed) czjb from recharge where SUBSTR(cjsj , 1 , 10) >= #{start} and SUBSTR(cjsj , 1 , 10) <=#{end} GROUP BY SUBSTR(cjsj , 1 , 10)  ")
    List<Recharge> getCz(@Param("start") String start, @Param("end") String end);

    @Select(" select * from recharge r where cjsj like '${time}%' ")
    List<Recharge> getDtCz(@Param("time") String time);

    @Select(" select * from exchange r where xfsj like '${time}%'")
    List<Exchange> getDtXf(@Param("time") String time);

    @Select(" select ifNULL(count(1),0) from user where cjsj like '${time}%'  and source = '0'")
    int countZc(String time);

    @Select(" select IFNULL(count(1),0) from user where cjsj like '${time}%' and source = '0'")
    int statisXzYh(String time);

    @Select(" select IFNULL(bz1,0), IFNULL((select count(1) from order_list where yhlx = '0' and pro_id in (select id from pro_info where pro_baseid = #{id}) ),0) count   from pro_baseinfo where id = #{id}")
    Map<String,String> getGmAndLl(String id);

    @Select(" select count(1) from order_list where pro_id in ( select id from pro_info where pro_baseid = #{id} ) ")
    double sumBfb(String time , String id);


    @Select("<script>" +
            "    select p.*, o.count count , o.count/cast(p.pro_price  as unsigned )  rate from pro_info p " +
            "INNER JOIN (select pro_id , IFNULL(count(1),0) count  from order_list where yhlx = '0' and pro_id in (select id from pro_info where pro_baseid = #{id}) group by pro_id) o " +
            "on o.pro_id = p.id and p.kjsj is not null  " +
            "<if test = 'time != null'>" +
            "  and p.kjsj like '${time}%' " +
            "</if>" +
            " <if test = 'proName != null '>" +
            "  and p.pro_name like '${proName}%' " +
            "</if>" +
            "   order by ${orderBy} " +
            "" +
            "</script> ")
    List<ProInfo> dgkj(@Param("id") String id, @Param("time") String time,@Param("proName") String proName,@Param("orderBy") String orderBy);

    @Select(" SELECT p.*,o.count  from pro_info p INNER JOIN (SELECT pro_id, IFNULL(count(pro_id),0) count from order_list where userid =  #{id}" +
            " <if test='time != null'>" +
            " and time like '${time}%' " +
            "</if>" +
            "  group by pro_id  ) o on p.id = o.pro_id  order by o.count desc  ")
    List<ProInfo> dqyh(@Param("id") String id , @Param("time") String time);

    @Select("<script>" +
            "   SELECT b.*,s.sm count from pro_baseinfo b inner join" +
            "( select p.pro_baseid, sum(o.c) sm from pro_info p INNER JOIN (select pro_id , count(1) c  from " +
            "order_list where yhlx = '0' and cjsj like '${time}%' group by pro_id) o on o.pro_id = p.id and p.kjsj is not null    group by p" +
            ".pro_baseid )  s on s.pro_baseid = b.id  " +
            " <if test='proName != null '>" +
            " and b.name = '${proName}%'" +
            "</if>" +
            "  order by ${orderBy}" +
            "</script> ")
    List<ProBaseinfo> kj(@Param("time") String time, @Param("proName") String proName, @Param("orderBy") String orderBy);

    @Select("<script> " +
            " select u.*, o.count from user u inner join " +
            "(select count(userid) count, userid from order_list where cjsj like '${time}%'  and yhlx  = '0' " +
            " <if test='name = null'>" +
            " and userid in (select id from user where user_name like '%${name}%' and source = '0' ) " +
            "</if>" +
            "  group by userid) o  on u.id = o.userid   order by o.count" +
            "</script> ")
    List<User> yhgm(@Param("time") String time,@Param("name") String name,@Param("orderBy") String orderBy);
}