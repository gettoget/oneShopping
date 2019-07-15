package com.ldz.biz.mapper;

        import com.ldz.biz.model.Exchange;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;
        import tk.mybatis.mapper.common.Mapper;

        import java.util.List;
        import java.util.Map;

public interface ExchangeMapper extends Mapper<Exchange> {

    @Select(" SELECT * from  ((select IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) today from exchange where xfsj LIKE '${today}%'  ) a , (SELECT IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) mon from exchange where xfsj like '${mon}%' ) b , (SELECT IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) total from exchange ) c ) ")
    Map<String,Integer> statisExCharge(@Param("today") String today,@Param("mon") String mon);

    @Select(" select IFNULL(SUM(CAST(xfjb as UNSIGNED)),0) from exchange where xfsj like '${today}%'")
    long sumAmount(@Param("today") String s);

    @Select(" SELECT co , convert(sum(a.jb),SIGNED) j from ( SELECT userid, IF(count(userid) >= 3,3,count(userid)) co,sum(xfjb) jb from exchange where xfsj like '%${time}%' group by userid ) a GROUP BY co")
    List<Map<String,Long>> statisXf(@Param("time") String time);

    @Select(" select SUBSTR(xfsj , 1 , 10) time ,convert(sum(xfjb),signed) je from exchange where SUBSTR(xfsj , 1 , 10) >= #{start} and SUBSTR(xfsj , 1 , 10) <=#{end} GROUP BY SUBSTR(xfsj , 1 , 10)  ")
    List<Exchange> statisXfje(@Param("start") String start,@Param("end") String end);

}