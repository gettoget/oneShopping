package com.ldz.biz.job;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.util.redis.RedisTemplateUtil;

/**
 * 10秒执行一次。待开奖商品执行开奖
 * 
 * @author Lee
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class ProInfoFinishSyncJob implements Job {
	Logger errorInfo = LoggerFactory.getLogger("error_info");
	
	@Autowired
	private RedisTemplateUtil mainRedis;
	@Autowired
    private OrderService orderService;
	@Autowired
	private Executor threadPool;

	@Override
	public void execute(JobExecutionContext context) {
		try{
			// 根据开奖商品时间排名，进行开奖线程执行。提取任务队列中在当前时间前6个小时的数据，为了容错
			Set<TypedTuple<Object>> objs = mainRedis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").rangeByScoreWithScores(DateTime.now().plusHours(-6).getMillis(), DateTime.now().getMillis());
    		Iterator<TypedTuple<Object>> ites =  objs.iterator();
    		while(ites.hasNext()){
    			TypedTuple<Object> item = ites.next();
    			DateTime exeTime = DateTime.now().withMillis(item.getScore().longValue());
    			String proId = item.getValue().toString();
    			if (exeTime.isAfter(DateTime.now())){
    				// 开奖时间已到，执行开奖任务
    				long removeFlag = mainRedis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").remove(proId);
    				if (removeFlag > 0){
    					threadPool.execute(() -> {
							try{
								orderService.fenpei(proId);
							}catch(Exception e){
								errorInfo.error("商品["+proId+"]开奖执行异常", e);
								//执行失败，再回填进队列，重新执行
								mainRedis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").add(proId, item.getScore());
							}
						});
    				}
    			}
    		}
    	}catch(Exception e){
    		errorInfo.error("商品开奖计划执行异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
    	}
	}
}
