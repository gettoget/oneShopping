package com.ldz.biz.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.ldz.biz.bean.JobConfig;
import com.ldz.biz.job.ProInfoFinishSyncJob;
import com.ldz.biz.job.RobotSyncJob;

@Component
public class ScheduleComponent {

    Logger errorLog = LoggerFactory.getLogger("error_info");

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    private List<JobConfig> jobConfigs = new ArrayList<>();

    @PostConstruct
    public void startScheduler() {
        // 每5秒运行一次。机器人购买商品功能
        jobConfigs.add(new JobConfig(RobotSyncJob.class,"0/5 * * * * ? *","RobotSyncJob"));

        // 每10秒运行一次。商品开奖功能
        jobConfigs.add(new JobConfig(ProInfoFinishSyncJob.class,"0/10 * * * * ? *","ProInfoFinishSyncJob"));

        try {
            for (JobConfig jobConfig : jobConfigs) {
                schedulerFactory.getScheduler().scheduleJob(jobConfig.getJobDetail(), jobConfig.getCronTrigger());
            }
        } catch (SchedulerException e) {
            errorLog.error("任务创建失败", e);
        }
    }
}
