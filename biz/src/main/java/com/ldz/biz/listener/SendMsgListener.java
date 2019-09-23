package com.ldz.biz.listener;

import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderListService;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.SendSmsUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
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
                        SendSmsUtil.sendMSG(user.getPhone(), "Produk yang anda ikuti sudah mendapatkan pemenang, yuk lihat sekarang");
                    });
                }
            }
            // 对参与商品超过 某个限定值的用户 进行随机 10-20%的金币反馈


        }
    }

}
