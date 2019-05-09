package com.ldz.biz;

import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProBaseinfoService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.UserService;
import com.ldz.util.commonUtil.*;
import com.ldz.util.redis.RedisTemplateUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    private ProBaseinfoService baseinfoService;
    @Autowired
    private SnowflakeIdWorker idWorker;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws IOException, InterruptedException {
        List<ProBaseinfo> all = baseinfoService.findAll();
        all.forEach(baseinfo -> proInfoService.saveOne(baseinfo.getId()));
        //List<ProInfo> allReprice = proInfoMapper.getAllReprice();
        //System.out.println(allReprice);
    	/*for (int i=0; i<10; i++){
    		String key = String.valueOf(idWorker.nextId());
    		for (int p=0; p<10; p++){
    			redis.boundListOps(key+"_nums").leftPush(String.valueOf(idWorker.nextId()));
    		}

    	}*/

    	/*ProInfo entity = new ProInfo();
    	entity.setProZt("1");
    	List<ProInfo> allReprice = proInfoService.findByEntity(entity);
    	for (int i=0; i<allReprice.size(); i++){
    		ProInfo item = allReprice.get(i);
    		//redis.delete(item.getId()+"_nums");
    		System.out.println(item.getProName()+"="+item.getRePrice()+"="+redis.boundSetOps(item.getId()+"_nums").size());
    		Integer price = new Integer(item.getRePrice());
    		for (int p=0; p<price;p++){
    			ProInfoLuckNumBean bean = new ProInfoLuckNumBean();
    			int num = 1000001 + (p+1);
    			bean.setProId(item.getId());
    			bean.setProName(item.getProName());
    			bean.setLuckNum(String.valueOf(num));
    			redis.boundSetOps(item.getId()+"_nums").add(bean);
    		}
    	}*/

    	/*for (int i=0; i<5; i++){
    		Set<Object> sets = redis.boundSetOps(User.class.getName()).distinctRandomMembers(5);
        	Iterator<Object> ites = sets.iterator();
        	while(ites.hasNext()){
        		User user = (User)ites.next();
        		System.out.println("当前第["+i+"]序列:"+user.getId()+"="+user.getUserName());
        	}
    	}*/
    	//如果商品已卖完，则更新商品状态
    	/*for (int i=0; i<10; i++){
    		int time = RandomUtils.nextInt(20);
    		if (time < 5){
    			time = -time;
    		}
    		redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").add(i, DateTime.now().plusMinutes(time).getMillis());
    	}*/
        
    	try{
    		
    		Set<Object> objst = redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").range(0, -1);
    		Iterator<Object> itest =  objst.iterator();
    		while(itest.hasNext()){
    			System.out.println("all:"+itest.next());
    		}
    		Set<TypedTuple<Object>> objs = redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").rangeByScoreWithScores(DateTime.now().plusDays(-1).getMillis(), DateTime.now().getMillis());
    		Iterator<TypedTuple<Object>> ites =  objs.iterator();
    		while(ites.hasNext()){
    			TypedTuple<Object> item = ites.next();
    			System.out.println("随机:"+DateTime.now().withMillis(item.getScore().longValue()).toString("yyyy-MM-dd HH:mm:ss")+"=="+item.getValue());
    		}
        	/*while(redis.keys("*_nums").size() > 0){
        		Set<Object> proInfos = redis.keys("*_nums");
        		if (CollectionUtils.isNotEmpty(proInfos)){
            		int randomIndex = RandomUtils.nextInt(proInfos.size());
            		if (randomIndex == proInfos.size()){
            			randomIndex = proInfos.size() - 1;
            		}
            		//得到商品ID
        			Object itemKey = CollectionUtils.get(proInfos.iterator(), randomIndex);
        			System.out.println("测试之前："+redis.boundSetOps(itemKey).size());
            		proInfoService.saveRobot(itemKey.toString());
            		System.out.println("测试之后："+redis.boundSetOps(itemKey).size());
            	}
        	}*/
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
