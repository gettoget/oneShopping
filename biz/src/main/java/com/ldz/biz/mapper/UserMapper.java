package com.ldz.biz.mapper;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import com.ldz.biz.model.UserInModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserMapper extends Mapper<User> {
    /**
     * 查询用户的充值和消费记录 列表
     * @param userId 用户id
     * @return
     */
    @Select(" SELECT * FROM (SELECT id ,xfsj sj,userid userId,xfjb amount,'1' as type  from exchange UNION all SELECT id ,cjsj sj,user_id userId,amonut amount,'2' as type  from recharge where czzt = '2') t where t.userid = #{userId} order by t.sj desc ")
    List<PaymentBean> getMyPayRecord(@Param("userId") String userId);

    @Select("SELECT * from  ((select count(1) today from user where cjsj LIKE '${today}%'  and source = '0') a , (SELECT count(1) mon from user where cjsj like '${mon}%' and source = '0') b , (SELECT count(1) total from user where source = '0') c ) ")
    Map<String,Integer> statisUser(@Param("today") String today,@Param("mon") String mon);

    @Select("<script> select a.userId, a.cys,b.cz,c.xf,d.czcg from " +
            " ( (select ifnull(count(distinct(pro_id)),0) cys ,user_id userId from order_form where user_id in <foreach collection='idList' item='id' index='index' open='('  close=')' separator=','> #{id}</foreach>  group by user_id) a " +
            ", (select ifnull(sum(CAST(ifnull(amonut,'0') as unsigned )),0) cz,user_id userId from recharge where user_id in <foreach collection='idList' item='id' index='index' open='('  close=')' separator=','> #{id}</foreach> group by user_id) b " +
            ", (select ifnull(sum(CAST(ifnull(xfjb,'0') as unsigned )),0) xf,userid userId from exchange where userid in <foreach collection='idList' item='id' index='index' open='('  close=')' separator=','> #{id}</foreach> group by userid) c , " +
            "  (select ifnull(sum(CAST(ifnull(czjb,'0') as unsigned )),0) czcg,user_id userId from recharge where czzt='2' and user_id in <foreach collection='idList' item='id' index='index' open='('  close=')' separator=','> #{id}</foreach> group by user_id) d ) WHERE a.userId = b.userId and b.userId = c.userId and c.userId = a.userId and a.userId=d.userId" +
            "</script>")
    List<UserInModel> sumCharge(@Param("idList") Set<String> id);

    @Select(" SELECT SUBSTR(cjsj , 1, 10) cjsj , count(1) bz1  from user where SUBSTR(cjsj , 1, 10) >= #{start} and SUBSTR(cjsj , 1, 10) <= #{end} GROUP BY SUBSTR(cjsj , 1, 10) ")
    List<User> countUsers(@Param("start") String start, @Param("end") String end);

    @Update("  update user set balance = (cast(balance as signed ) + cast(#{amount} as signed)) where id = #{userId}")
    void saveBalance(@Param("userId") String userId, @Param("amount") String amount);

    @Select("<script> " +
            " select user_id  from recharge r where czqd = '1' group by user_id having" +
            " <if test='num==1 || num==2'>" +
            " count(user_id) = #{num} " +
            "</if>" +
            "<if test='num == 3'>" +
            " count(user_id) >= #{num} " +
            " </if> " +
            "</script> ")
    List<String> getUserIds(@Param("num") Integer num);

}