package com.ldz.biz.config;

import com.ldz.biz.bean.JobConfig;
import com.ldz.biz.job.ForceKj;
import com.ldz.biz.job.KjJob;
import com.ldz.biz.job.ProInfoFinishSyncJob;
import com.ldz.biz.job.RobotSyncJob;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleComponent {

    Logger errorLog = LoggerFactory.getLogger("error_info");

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    private List<JobConfig> jobConfigs = new ArrayList<>();

    @PostConstruct
    public void startScheduler() {
//        //         每5秒运行一次。机器人购买商品功能
//        jobConfigs.add(new JobConfig(RobotSyncJob.class,"0/10 * * * * ? *","RobotSyncJob"));
////
////        // 每10秒运行一次。商品开奖功能
//        jobConfigs.add(new JobConfig(ProInfoFinishSyncJob.class,"0/10 * * * * ? *","ProInfoFinishSyncJob"));
////
////        // 每5分钟检查一次。商品开奖功能
//        jobConfigs.add(new JobConfig(KjJob.class,"0 */5 * * * ?","kjJob"));
//
//        // 每5分钟检查一次。商品开奖功能
//        jobConfigs.add(new JobConfig(ForceKj.class,"0 */5 * * * ?","forceKjJob"));

        try {
            for (JobConfig jobConfig : jobConfigs) {
                schedulerFactory.getScheduler().scheduleJob(jobConfig.getJobDetail(), jobConfig.getCronTrigger());
            }
        } catch (SchedulerException e) {
            errorLog.error("任务创建失败", e);
        }
    }
}
