package com.ldz.biz.listener;

import com.ldz.biz.service.ProInfoService;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class GroundingListener implements MessageListener {

    private RedisTemplateUtil redisTemplate;

    private ProInfoService proInfoService;

    public GroundingListener(RedisTemplateUtil redisTemplate, ProInfoService proInfoService) {
        this.redisTemplate = redisTemplate;
        this.proInfoService = proInfoService;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        val redisChannel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
        val eventMessage = redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topicc = redisChannel;
        if (StringUtils.equals(topicc, "grounding")) {
            String id = (String) eventMessage;
            proInfoService.saveOne(id);

        }
    }
}
