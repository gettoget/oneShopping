package com.ldz.biz.job;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ForceKj implements Job {
    @Autowired
    private ProInfoService proInfoService;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        condition.eq(ProInfo.InnerColumn.proZt, "1");
        condition.lte(ProInfo.InnerColumn.rePrice, 1);
        List<ProInfo> infos = proInfoService.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(infos)){
            infos.forEach(proInfo -> {
                Object o = redis.boundValueOps(proInfo.getId() + "_nums").get();
                if(o == null){
                    // redis中没有值 可以直接开奖
                    orderService.fenpei(proInfo.getId());
                }
            });
        }

    }
}



