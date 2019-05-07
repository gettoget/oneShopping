package com.ldz.biz;

import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.UserService;
import com.ldz.util.commonUtil.*;
import com.ldz.util.redis.RedisTemplateUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BizApplicationTests {


    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProInfoService proInfoService;

    @Autowired
    private SnowflakeIdWorker idWorker;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws IOException, InterruptedException {
        //List<ProInfo> allReprice = proInfoMapper.getAllReprice();
        //System.out.println(allReprice);
    	/*for (int i=0; i<10; i++){
    		String key = String.valueOf(idWorker.nextId());
    		for (int p=0; p<10; p++){
    			redis.boundListOps(key+"_nums").leftPush(String.valueOf(idWorker.nextId()));
    		}
    		
    	}*/
    	
    	/*for (int i=0; i<50; i++){
    		//redis.boundSetOps("572827119164325888_nums").add(String.valueOf(idWorker));
    	}*/
    	
    	/*for (int i=0; i<5; i++){
    		Set<Object> sets = redis.boundSetOps(User.class.getName()).distinctRandomMembers(5);
        	Iterator<Object> ites = sets.iterator();
        	while(ites.hasNext()){
        		User user = (User)ites.next();
        		System.out.println("当前第["+i+"]序列:"+user.getId()+"="+user.getUserName());
        	}
    	}*/
    	try{
    		System.out.println("测试之前："+redis.boundSetOps("572827119164325888_nums").size());
        	Set<Object> proInfos = redis.keys("*_nums");
        	if (CollectionUtils.isNotEmpty(proInfos)){
        		int randomIndex = RandomUtils.nextInt(proInfos.size());
        		if (randomIndex == proInfos.size()){
        			randomIndex = proInfos.size() - 1;
        		}
        		//得到商品ID
    			Object itemKey = CollectionUtils.get(proInfos.iterator(), randomIndex);
        		proInfoService.saveRobot(itemKey.toString());
        	}
        	System.out.println("测试之后："+redis.boundSetOps("572827119164325888_nums").size());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	

    }

}
