package com.ldz.biz.mapper;

import com.ldz.biz.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionMapper extends Mapper<Question> {
    @Select("<script>" +
            " SELECT q.*,u.user_name username,u.h_img himg from question q, " +
            "(SELECT u.user_name,m.id,u.h_img from (SELECT user_id,MAX(id) id from question GROUP BY user_id order by id desc) m , user u " +
            "where u.id = m.user_id " +
            "<if test='name != null'>" +
            " and u.user_name like '%${name}%'" +
            "</if> )" +
            " u where u.id = q.id  " +
            "</script>")
    List<Question> getPeoGroup(@Param("name") String name);

    @Update(" update question set ck = '1' where user_id = #{userId}")
    void updateCk(@Param("userId") String userId);

    @Select(" select * from question where user_id = #{id} order by id limit 1")
    Question ck(String id);
}
