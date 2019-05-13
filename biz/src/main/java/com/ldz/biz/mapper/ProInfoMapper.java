package com.ldz.biz.mapper;

import com.ldz.biz.model.ProInfo;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ProInfoMapper extends Mapper<ProInfo> {

    @Select("select * from pro_info where pro_baseid = #{id} and  pro_Zt = '4' order by kjsj desc limit 1")
    ProInfo getLatestPerson(@Param("id")String id);

    @Select(" select pro_baseid from pro_info where id = #{id}")
    String getBaseId(@Param("id") String id);

    @Select(" select * from pro_info where pro_zt = '1' and (CAST(re_price as unsigned)/CAST(pro_price as unsigned) < 0.1)  order by CAST( re_price as unsigned ) ")
    List<ProInfo> getRePager();

    @Select(" select id, re_price rePrice, pro_name proName from pro_info where r_type ='2' and pro_zt = '1'")
    List<ProInfo> getAllReprice();

    @Update(" update pro_info set gxsj = CURRENT_TIMESTAMP(3) , pro_zt = '3' , kjsj = date_add(CURRENT_TIMESTAMP(3), interval 1 minute), cyyhs = CAST(cyyhs as unsigned) +1 where id = #{id} and  re_price = 0 and pro_zt = '1'")
    int updateFinish(@Param("id") String id);

    @Update("update pro_info set gxsj = CURRENT_TIMESTAMP(3), cyyhs = CAST(cyyhs as unsigned) +1 where id = #{id} and pro_zt = '1'  ")
    int updateProInfo(@Param("id") String id);

    @Select(" select id from pro_info where pro_baseid = ( select pro_baseid from pro_info where id = #{id} ) order by cjsj desc limit 1")
    String getLatestPro(String id);

}