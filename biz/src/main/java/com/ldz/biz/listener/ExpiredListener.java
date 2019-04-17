package com.ldz.biz.listener;

import com.ldz.biz.service.OrderService;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class ExpiredListener implements MessageListener {

    private RedisTemplateUtil redisTemplate;

    private OrderService orderService;

    public ExpiredListener(RedisTemplateUtil redisTemplate, OrderService orderService) {
        this.redisTemplate = redisTemplate;
        this.orderService = orderService;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
        val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topic = redisChannel;
        String value = (String) eventMessage;
        if(StringUtils.contains(topic, "expired")){
            if(StringUtils.contains(value,"_dzf")){
                String[] split = value.split("_");
                // 订单取消支付
                orderService.orderCancel(split[0]);
            }
        }



    }
}
