package com.ldz.biz;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;

public class Test {


    public static void main(String[] args) {
        String apiKey = "slu";
        String secretKey = "sly";
        PushKeyPair keyPair = new PushKeyPair(apiKey, secretKey);
        BaiduPushClient pushClient = new BaiduPushClient(keyPair, BaiduPushConstants.CHANNEL_REST_URL);
        pushClient.setChannelLogHandler(yunLogEvent -> {
            System.out.println(yunLogEvent.getMessage());
        });





    }


}
