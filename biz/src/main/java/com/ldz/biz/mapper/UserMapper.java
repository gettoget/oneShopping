package com.ldz.biz.mapper;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    /**
     * 查询用户的充值和消费记录 列表
     * @param userId 用户id
     * @return
     */
    @Select(" SELECT * FROM (SELECT id ,xfsj sj,userid userId,xfjb je,'1' as type  from exchange UNION all SELECT id ,cjsj sj,user_id userId,amonut je,'2' as type  from recharge) t where t.userid = #{userId} order by t.sj desc ")
    List<PaymentBean> getMyPayRecord(@Param("userId") String userId);

}