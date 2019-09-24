package com.ldz.biz.listener;

import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderListService;
import com.ldz.biz.service.RechargeService;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.SendSmsUtil;
import com.ldz.util.commonUtil.SnowflakeIdWorker;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SendMsgListener implements MessageListener {

    private RedisTemplateUtil redisTemplate;

    private OrderListService listService;

    private RechargeMapper mapper;

    private UserMapper baseMapper;

    private SnowflakeIdWorker idWorker;

    private RechargeService rechargeService;

    public SendMsgListener(RedisTemplateUtil redisTemplate, OrderListService listService, RechargeMapper mapper,
                           UserMapper baseMapper, SnowflakeIdWorker idWorker, RechargeService rechargeService) {
        this.redisTemplate = redisTemplate;
        this.listService = listService;
        this.mapper = mapper;
        this.baseMapper = baseMapper;
        this.idWorker = idWorker;
        this.rechargeService = rechargeService;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
        val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topic = redisChannel;
        if (StringUtils.equals(topic, "sendMsg")) {

            String id = (String) eventMessage;
            // 拿到商品开奖商品 id
            // 查询所有参与的真实用户 , 并查找有两次以上意愿充值的用户 , 发送开奖短信
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.proId, id);
            condition.eq(OrderList.InnerColumn.yhlx, "0");
            List<OrderList> lists = listService.findByCondition(condition);

            if(CollectionUtils.isNotEmpty(lists)){
                // 拿到所有的参与用户id  筛选出 有两次以上充值用户的id
                Set<String> ids = lists.stream().map(OrderList::getUserid).collect(Collectors.toSet());
                List<User> gteTwo = mapper.getTwo(ids);
                if(CollectionUtils.isNotEmpty(gteTwo)){
                    gteTwo.forEach(user -> {
                        SendSmsUtil.sendMSG(user.getPhone(), "produk yang anda ikuti sudah berakhir, yuk lihat hasilnya sekarang");
                    });
                }
            }
            // 对参与商品超过 某个限定值的用户 进行随机 10-20%的金币反馈
            // 获取超过购买达到 5 个币的用户 , 返还金币
            List<User> users = mapper.getMoreThanFive(id);
            for (User user : users) {
                int xf = Integer.parseInt(user.getXf());
                // 拿到消费的金币 随机 10% - 20%
                int persent = RandomUtils.nextInt(0, 2);
                int jb;
                if(persent == 1){
                    jb = xf * 20 / 100;
                }else{
                    jb = xf*10/100;
                }
                if(jb == 0){
                    jb =1;
                }
                // 用户余额加奖励金币 生成充值记录
                baseMapper.saveBalance(user.getId(),  "" + jb);
                Recharge recharge = new Recharge();
                recharge.setAmonut("2");
                recharge.setCzzt("2");
                recharge.setCjsj(DateUtils.getNowTime());
                recharge.setCzjb("2");
                recharge.setCzqd("2");
                recharge.setUserId(user.getId());
                recharge.setCzqjbs(user.getBalance());
                recharge.setCzhjbs(Integer.parseInt(user.getBalance()) + jb + "");
                recharge.setBz2("return");
                recharge.setId(idWorker.nextId() + "");
                rechargeService.save(recharge);
                // 短信发送
                SendSmsUtil.sendMSG(user.getPhone(), "produk yang anda ikuti sudah berakhir, selamat, anda berhak mendapatkan bonus "+jb + " Gcoin");
            }

        }
    }

}
