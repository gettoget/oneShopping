package com.ldz.biz.mapper;

import com.ldz.biz.model.LuckNum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LuckNumMapper extends Mapper<LuckNum> {


    @Select(" select num from luck_num where sort > #{sort} and sort <= #{num} and pro_baseid = #{baseId}")
    List<String> getLuckNum(@Param("sort'") int sort,@Param("num") int num, @Param("baseId") String baseId);


}
