package com.ldz.biz.job;

import com.ldz.biz.service.ProInfoService;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 5秒执行一次。机器人商品购买
 *
 * @author Lee
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
@SuppressWarnings("deprecation")
public class RobotSyncJob implements Job {
	Logger errorInfo = LoggerFactory.getLogger("error_info");

	@Autowired
	private RedisTemplateUtil mainRedis;
	@Autowired
    private ProInfoService infoService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		//从redis队列中提取还有剩余名额的商品
    		Set<Object> proInfos = mainRedis.keys("*_nums");
        	if (CollectionUtils.isNotEmpty(proInfos)){

				int[] ints = randomNumber(0, proInfos.size(), 10);
				for (int i : ints) {
					Object itemKey = CollectionUtils.get(proInfos.iterator(), i);
					try {
						infoService.saveRobot(itemKey.toString());
					}catch (Exception e){
						errorInfo.error(itemKey + "商品剩余名额分配异常", e);
					}
				}
				/*int randomIndex = RandomUtils.nextInt(proInfos.size());
        		*//*int randomIndex = RandomUtils.nextInt(proInfos.size());
        		if (randomIndex == proInfos.size()){
        			randomIndex = proInfos.size() - 1;
        		}*//*
        		//随机得到商品ID进行购买操作  修改为所有商品都购买
				for (int i = 0; i < proInfos.size(); i++) {

				}*/
//				Object itemKey = CollectionUtils.get(proInfos.iterator(), randomIndex);
//        		infoService.saveRobot(itemKey.toString());
        	}
    	}catch(Exception e){
    		errorInfo.error("商品剩余名额分配异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
    	}
	}


	private static int[] randomNumber(int min, int max, int n){

		//判断是否已经达到索要输出随机数的个数
		if(n>(max-min+1) || max <min){
			return null;
		}

		int[] result = new int[n]; //用于存放结果的数组

		int count = 0;
		while(count <n){
			int num = (int)(Math.random()*(max-min))+min;
			boolean flag = true;
			for(int j=0;j<count;j++){
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count++;
			}
		}
		return result;
	}

}
