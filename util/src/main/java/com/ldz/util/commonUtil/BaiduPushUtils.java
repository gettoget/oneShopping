package com.ldz.util.commonUtil;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaiduPushUtils {

   private static Logger errorLog = LoggerFactory.getLogger("error_info");


    public static void pushSingleMsg(String chnnelId, int messageType, String message, int deviceType) throws PushClientException, PushServerException {
        String apiKey = "zid2bvrlXw2KjoD85RfgFPAu";
        String secretKey = "FlfACjZ6MCQZ52kAAA3vGv4nPcFu713Y";

        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);


        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler(event -> System.out.println(event.getMessage()));

        try {
            // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                    addChannelId(chnnelId).
                    addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                    addMessageType(messageType).              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                    addMessage(message).
                    addDeviceType(deviceType);      //设置设备类型，deviceType => 1 for web, 2 for pc,
            //3 for android, 4 for ios, 5 for wp.
            // 5. 执行Http请求

            PushMsgToSingleDeviceResponse response = pushClient.
                    pushMsgToSingleDevice(request);
            // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                errorLog.error("推送信息异常 :", e);
            } else {
                errorLog.error("推送信息异常 :", e);
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                errorLog.error("推送信息异常 :", e);
            } else {
                errorLog.error("推送信息异常 :", e);
            }
        }
    }


    public static void pushAllMsg(int messageType, String message, int deviceType, long sendTime)  {


        // 1. get apiKey and secretKey from developer console
        String apiKey = "zid2bvrlXw2KjoD85RfgFPAu";
        String secretKey = "FlfACjZ6MCQZ52kAAA3vGv4nPcFu713Y";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(event -> System.out.println(event.getMessage()));
//        JSONArray jsonArray = new JSONArray();

        try {
            // 4. specify request
            PushMsgToAllRequest request;
            if(sendTime == 0){
               request = new PushMsgToAllRequest()
                        .addMsgExpires(new Integer(3600))
                        .addMessageType(messageType)
                        .addMessage(message)
                        // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例70秒后推送
//                        .addSendTime(sendTime)
                        .addDeviceType(deviceType);
            }else{
                request = new PushMsgToAllRequest()
                        .addMsgExpires(new Integer(3600))
                        .addMessageType(messageType)
                        .addMessage(message)
                        // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例70秒后推送
                        .addSendTime(sendTime)
                        .addDeviceType(deviceType);
            }

            // 5. http request
            PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
            // Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
                    + response.getSendTime() + ",timerId: "
                    + response.getTimerId());
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                errorLog.error("推送信息异常 :", e);
            } else {
                errorLog.error("推送信息异常 :", e);
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                errorLog.error("推送信息异常 :", e);
            } else {
                errorLog.error("推送信息异常 :", e);
            }
        }
    }


}
