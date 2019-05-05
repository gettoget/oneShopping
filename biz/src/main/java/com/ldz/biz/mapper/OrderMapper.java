package com.ldz.biz.mapper;

import com.ldz.biz.model.Order;
import com.ldz.biz.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {

    /**
     * 获取最后 50 笔交易的时间
     */
    @Select(" select * from order_form where zfsj is not null and zfsj != '' and pro_id =  #{id}  order by zfsj desc limit #{limi}")
    List<Order> getLastFifty(@Param("id") String id, @Param("limi") int limi);

    /**
     * 充值所有未中奖状态
     *
     */
    @Update(" update order_form set ddzt = '2', zjhm = #{zjhm} where pro_id = #{id} and ddzt = '0'")
    void updateDdztToLost(@Param("id") String id,@Param("zjhm") String zjhm);


    /**
     * 库存减1
     */
    @Update("update pro_baseinfo set pro_store = CAST(pro_store as unsigned) - #{gmfs} where id = #{baseId}")
    void minusStore(@Param("baseId") String baseId,@Param("gmfs") int gmfs);

    /**
     * 剩余名额减掉购买份数
     */
    @Update("update pro_info set re_price = CAST(re_price as unsigned) - #{gmfs} where id  = #{proId}")
    void minusRePrice(@Param("gmfs")int gmfs,@Param("proId") String proId);

    /**
     * 库存添加
     */
    @Update(" update pro_baseinfo set pro_store = CAST(pro_store as unsigned) + #{gmfs} where id = #{baseId}")
    void plusStore(@Param("baseId") String baseId,@Param("gmfs") int gmfs);

    /**
     * 剩余名额添加
     */
    @Update(" update pro_info set re_price = CAST(re_price as unsigned) + #{gmfs} where id = #{proId}")
    void plusRePrice(@Param("proId")String proId,@Param("gmfs") int gmfs);

    @Select("SELECT *,RAND() as r FROM user where source = '1' ORDER BY r LIMIT #{i}")
    List<User> ranUsers(@Param("i") int i);

    @Update("update pro_info set cyyhs = CAST(cyyhs as unsigned ) + 1 where id = #{id}")
    void updateCyyhs(@Param("id") String id);
}