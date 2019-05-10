package com.ldz.biz.mapper;

import com.ldz.biz.model.ProBaseinfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProBaseinfoMapper extends Mapper<ProBaseinfo> {


    @Select(" select id from pro_info where pro_zt = '4' and  pro_baseid = #{baseId} order by kjsj desc limit 50")
    List<String> getFifProId(@Param("baseId") String baseId);


}