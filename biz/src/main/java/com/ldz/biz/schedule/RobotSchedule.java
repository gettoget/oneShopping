package com.ldz.biz.schedule;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ldz.biz.service.ProInfoService;
import com.ldz.util.redis.RedisTemplateUtil;

@Component
@SuppressWarnings("deprecation")
public class RobotSchedule {
	
	Logger errorLog = LoggerFactory.getLogger("error_info");

    @Autowired
    private ProInfoService infoService;
    @Autowired
    private RedisTemplateUtil redis;

    public void saveRobot() {
    	try{
    		//从redis队列中提取还有剩余名额的商品
    		Set<Object> proInfos = redis.keys("*_nums");
        	if (CollectionUtils.isNotEmpty(proInfos)){
        		int randomIndex = RandomUtils.nextInt(proInfos.size());
        		if (randomIndex == proInfos.size()){
        			randomIndex = proInfos.size() - 1;
        		}
        		//随机得到商品ID进行购买操作
				Object itemKey = CollectionUtils.get(proInfos.iterator(), randomIndex);
        		infoService.saveRobot(itemKey.toString());
        	}
    	}catch(Exception e){
    		errorLog.error("商品剩余名额分配异常", e);
    	}
    }
}
