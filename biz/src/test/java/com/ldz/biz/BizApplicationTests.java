package com.ldz.biz;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.model.ProBaseinfo;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.*;
import com.ldz.util.bean.AndroidMsgBean;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.redis.RedisTemplateUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderListService orderListService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws IOException, InterruptedException, PushClientException, PushServerException {
        AndroidMsgBean msgBean = new AndroidMsgBean();
        ProInfo proInfo = proInfoService.findById("568838409968156672");
        msgBean.setJson(JsonUtil.toJson(proInfo));
        msgBean.setType("4");
        BaiduPushUtils.pushAllMsg(0,JsonUtil.toJson(msgBean),3,System.currentTimeMillis()/1000 + 70);

       /* List<ProBaseinfo> all = baseinfoService.findAll();
        List<String> list = all.stream().map(ProBaseinfo::getId).collect(Collectors.toList());
        for (String s : list) {
            proInfoService.saveOne(s);
            proInfoService.saveOne(s);
        }*/
       /* Set<Object> keys = redis.keys("*_nums");
        Iterator<Object> iterator = keys.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            Long size = redis.boundSetOps(next).size();
            System.out.println(size);
        }*/

        /*Set<Object> keys = redis.keys("*_nums");
        Iterator<Object> iterator = keys.iterator();
        while (iterator.hasNext()){

            String next = (String) iterator.next();
            SimpleCondition condition =  new SimpleCondition(ProInfo.class);
            String s = next.split("_")[0];
            *//*condition.eq(ProInfo.InnerColumn.id, s);
            condition.eq(ProInfo.InnerColumn.proZt, "1");
            List<ProInfo> proInfos = proInfoService.findByCondition(condition);*//*
            // List<String> ids = proInfos.stream().map(ProInfo::getId).collect(Collectors.toList());
            ProInfo info = proInfoService.findById(s);
            int parseInt = Integer.parseInt(info.getRePrice());
            int anInt = Integer.parseInt(info.getProPrice());

            condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.proId, s);
            List<OrderList> orderLists = orderListService.findByCondition(condition);
            List<String> nums = orderLists.stream().map(OrderList::getNum).collect(Collectors.toList());
            Long size = redis.boundSetOps(next).size();
            if(anInt - parseInt == nums.size() && size != parseInt){
                Set<Object> set = redis.boundSetOps(next).distinctRandomMembers(size);
                Iterator<Object> iterator1 = set.iterator();
                while (iterator1.hasNext()){
                    Object next1 = iterator1.next();
                    ProInfoLuckNumBean numBean = (ProInfoLuckNumBean) next1;
                    if(nums.contains(numBean.getLuckNum())){
                        redis.boundSetOps(next).remove(next1);
                    }
                }*/
                /*for (Object o : set) {
                    String num = (String) o;
                    if(nums.contains(num)){
                        redis.boundSetOps(next).remove(o);
                    }
                }*/
            }


/*
            if(!ids.contains(s)){
                redis.delete(next);
            }*/

        }

       /* DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        List<OrderList> lastFifty = orderMapper.getLastFifty("576416482989178880", 50);
        Long hHmmssSSS = lastFifty.stream().map(OrderList::getCjsj).map(s -> Long.parseLong(DateTime.parse(s, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
        String zjhm = (hHmmssSSS % Long.parseLong("7699")) + 10000001 + "";*/
//        orderService.fenpei("576416482989178880");
//        System.out.println(zjhm);



        /*long millis = DateTime.now().plusMinutes(1).getMillis();
        redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").add("576913984226066432", millis);*/
        /*List<ProBaseinfo> all = baseinfoService.findAll();
        all.forEach(baseinfo -> proInfoService.saveOne(baseinfo.getId()));*/
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
    			int num = 10000001 + (p+1);
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

//    	try{
//
//    		Set<Object> objst = redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").range(0, -1);
//    		Iterator<Object> itest =  objst.iterator();
//    		while(itest.hasNext()){
//    			System.out.println("all:"+itest.next());
//    		}
//    		Set<TypedTuple<Object>> objs = redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").rangeByScoreWithScores(DateTime.now().plusDays(-1).getMillis(), DateTime.now().getMillis());
//    		Iterator<TypedTuple<Object>> ites =  objs.iterator();
//    		while(ites.hasNext()){
//    			TypedTuple<Object> item = ites.next();
//    			System.out.println("随机:"+DateTime.now().withMillis(item.getScore().longValue()).toString("yyyy-MM-dd HH:mm:ss")+"=="+item.getValue());
//    		}
//        	/*while(redis.keys("*_nums").size() > 0){
//        		Set<Object> proInfos = redis.keys("*_nums");
//        		if (CollectionUtils.isNotEmpty(proInfos)){
//            		int randomIndex = RandomUtils.nextInt(proInfos.size());
//            		if (randomIndex == proInfos.size()){
//            			randomIndex = proInfos.size() - 1;
//            		}
//            		//得到商品ID
//        			Object itemKey = CollectionUtils.get(proInfos.iterator(), randomIndex);
//        			System.out.println("测试之前："+redis.boundSetOps(itemKey).size());
//            		proInfoService.saveRobot(itemKey.toString());
//            		System.out.println("测试之后："+redis.boundSetOps(itemKey).size());
//            	}
//        	}*/
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}



