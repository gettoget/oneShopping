package com.ldz.biz.mapper;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProInfoMapper extends Mapper<ProInfo> {

    @Select("select * from pro_info where pro_baseid = #{id} and  pro_Zt = '4' order by kjsj desc limit 1")
    ProInfo getLatestPerson(@Param("id")String id);

    @Select(" select pro_baseid from pro_info where id = #{id}")
    String getBaseId(@Param("id") String id);

    @Select(" select * from pro_info where pro_zt = '1' order by CAST( re_price as unsigned ) ")
    List<ProInfo> getRePager();
}