package com.ldz.biz.job;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.bean.SimpleCondition;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class KjJob implements Job {

    @Autowired
    private ProInfoService infoService;

    @Autowired
    private OrderService orderService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 每5分钟查询一次状态为待开奖 ,但是没有开奖的商品 (时间跨度较大的商品)
        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        String minutes = DateTime.now().minusMinutes(5).toString("yyyy-MM-dd HH:mm:ss");
        condition.lte(ProInfo.InnerColumn.kjsj,minutes);
        condition.eq(ProInfo.InnerColumn.proZt, "3");
        condition.and().andIsNotNull(ProInfo.InnerColumn.kjsj.name());
        List<ProInfo> infos = infoService.findByCondition(condition);
        infos.stream().forEach(proInfo -> orderService.fenpei(proInfo.getId()));
    }
}
