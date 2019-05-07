package com.ldz.biz.mapper;

import com.ldz.biz.model.ProEval;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ProEvalMapper extends Mapper<ProEval> {

    /**
     * 点赞数加一
     * @param id
     */
    @Update(" update pro_eval set bz1 = CAST(IFNULL(bz1,'0') as unsigned) +1 where id = #{id}")
    void plusThumbs(@Param("id") String id);

    /**
     * 点赞数减一
     * @param id
     */
    @Update(" update pro_eval set bz1 = CAST(IFNULL(bz1,'0') as unsigned) - 1 where id = #{id}")
    void minusThumbs(@Param("id")String id);

}