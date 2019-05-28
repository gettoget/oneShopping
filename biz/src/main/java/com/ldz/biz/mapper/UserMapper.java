package com.ldz.biz.mapper;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {
    /**
     * 查询用户的充值和消费记录 列表
     * @param userId 用户id
     * @return
     */
    @Select(" SELECT * FROM (SELECT id ,xfsj sj,userid userId,xfjb amount,'1' as type  from exchange UNION all SELECT id ,cjsj sj,user_id userId,amonut amount,'2' as type  from recharge) t where t.userid = #{userId} order by t.sj desc ")
    List<PaymentBean> getMyPayRecord(@Param("userId") String userId);

    @Select("SELECT * from  ((select count(1) today from user where cjsj LIKE '${today}%'  and source = '0') a , (SELECT count(1) mon from user where cjsj like '${mon}%' and source = '0') b , (SELECT count(1) total from user where source = '0') c ) ")
    Map<String,Integer> statisUser(@Param("today") String today,@Param("mon") String mon);

}