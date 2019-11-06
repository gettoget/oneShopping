package com.ldz.biz.job;

import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.util.bean.AndroidMsgBean;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.BaiduPushUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Log4j2
public class ForceKj implements Job {
    @Autowired
    private ProInfoService proInfoService;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        condition.eq(ProInfo.InnerColumn.proZt, "1");
        condition.lte(ProInfo.InnerColumn.rePrice, "1");
        List<ProInfo> infos = proInfoService.findByCondition(condition);
        log.info(" infos size " + infos.size());
        if(CollectionUtils.isNotEmpty(infos)){

            infos.forEach(proInfo -> {
                Long o = redis.boundSetOps(proInfo.getId() + "_nums").size();
                log.info(" long ----->" + o);
                System.out.println("long ---->" + o);
                if(o <= 0 ){
                    // 更新商品状态为 待开奖
                    String proId = proInfo.getId();
                    proInfo.setRePrice("0");
                    proInfoService.update(proInfo);
                    orderMapper.updateFinish(proId);
                    // 号码分配完 清理redis key
                    redis.delete(proId + "_nums");
                    // 将待开奖的商品持久化存储起来，防止服务异常停止，造成商品无法开奖。同时建立延时任务 , 分配中奖号码
                    // 商品结束后1分钟执行开奖功能
                    long millis = DateTime.now().plusMinutes(1).getMillis();
                    redis.boundZSetOps(ProInfo.class.getSimpleName() + "_award").add(proId, millis);
                    // 商品进入待开奖状态 , 向前台推送信息
                    AndroidMsgBean msgBean = new AndroidMsgBean();
                    msgBean.setType("3");
                    msgBean.setJson(JsonUtil.toJson(proInfo));
                    BaiduPushUtils.pushAllMsg(0, JsonUtil.toJson(msgBean), 3, 0);
                }
            });
        }

    }
}



