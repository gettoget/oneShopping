package com.ldz.biz.appctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldz.biz.bean.PaySuc;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.UserService;
import com.ldz.util.bean.AndroidPushBean;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.BaiduPushUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/pay")
public class PayCtrl {

    @Autowired
    private RechargeService service;
    @Autowired
    private ProInfoService infoService;
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplateUtil redis;
    /**
     * 支付回调接口
     *
     * @return
     */
    @PostMapping(value = "/paySuc")
    public ApiResponse<String> paySuc(String data) {
        RuntimeCheck.ifBlank(data, "DATA IS NULL");
        PaySuc suc = JSON.parseObject(data, PaySuc.class);
        return service.paySuc(suc.getAmount(), suc.getTrans_id(), suc.getWords(), data);
    }

    @PostMapping("/paySucTest")
    public ApiResponse<String> paySucTest(String id) {
        return service.paySucTest(id);
    }

    @GetMapping("/testMsg")
    public ApiResponse<String> testMsg() {

        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        condition.eq(ProInfo.InnerColumn.proZt, "4");
        condition.setOrderByClause(" kjsj desc");
        List<ProInfo> infos = infoService.findByCondition(condition);
        ProInfo info = infos.get(0);

        AndroidPushBean pushBean = new AndroidPushBean();
        pushBean.setDescription("Produk yang anda ikuti sudah mendapatkan pemenang, yuk lihat sekarang");
        pushBean.setOpen_type(2);
        pushBean.setNotification_builder_id(0);
        pushBean.setNotification_basic_style(7);
        pushBean.setUrl("");
        info.setTip("Hadiah atas partisipasi anda, silahkan dilihat!");
        User byId = userService.findById(info.getUserId());
        info.setUserName(byId.getUserName());
        pushBean.setCustom_content(JsonUtil.toJson(info));
        String o = (String) redis.boundValueOps("579590080797081600_channelId").get();
        JSONObject object = new JSONObject();
        object.put("title","");
        object.put("description","Produk yang anda ikuti sudah mendapatkan pemenang, yuk lihat sekarang");
        object.put("notification_builder_id",0);
        object.put("notification_basic_style",7);
        object.put("open_type",2);
        object.put("url","");
        object.put("pkg_content","component=com.xccmpinc.yypay/.ui.MainActivity");
        object.put("custom_content",JSON.toJSON(info));
        BaiduPushUtils.pushSingleMsg(o, 1, JSON.toJSONString(object), 3);

        return ApiResponse.success();
    }

}
