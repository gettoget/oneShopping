package com.ldz.biz.mapper;

import com.ldz.biz.model.Bank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface BankMapper extends Mapper<Bank> {

    @Select("<script> " +
            "    SELECT *,RAND() r from bank  " +
            "    where bank_no not in  (SELECT bank_no from  (SELECT bank_no , count(bank_no) c " +
            "    from bank_record where cjsj like '%${today}%' group by bank_no  HAVING c >= #{czqr}) b ) " +
            "    <if test = 'type != null '>and type = #{type} </if> " +
            "    order by r LIMIT 1" +
            "</script>")
    Bank getByType(@Param("type") String type,@Param("today") String today, @Param("czqr")int czqr);


}
