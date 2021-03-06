package com.ldz.biz.appctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldz.biz.bean.PaySuc;
import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.model.OrderList;
import com.ldz.biz.model.ProInfo;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.OrderListService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.UserService;
import com.ldz.util.bean.AndroidPushBean;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.BaiduPushUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.ShareCodeUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private OrderListService listService;

    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RechargeService rechargeService;
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
    public ApiResponse<String> testMsg(String id) {

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
        String o = (String) redis.boundValueOps(id + "_channelId").get();
        JSONObject object = new JSONObject();
        object.put("title","GoSaku");
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

    /**
     *  前面注册的补送 5 个 币
     */
    @GetMapping("/updateBalance")
    public ApiResponse<String> updateBalance(){

        // 查出所有注册但是没有赠送 5 金币的用户
        List<User> users = userService.findEq(User.InnerColumn.source, "0");
        List<String> list = users.stream().map(User::getId).collect(Collectors.toList());
        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.eq(Recharge.InnerColumn.czqd, "2");
        condition.eq(Recharge.InnerColumn.amonut, "5");
        List<Recharge> recharges = rechargeService.findByCondition(condition);
        List<String> strings = recharges.stream().map(Recharge::getUserId).collect(Collectors.toList());
        for (String s : list) {
            if(!strings.contains(s)){
                template.execute(" update  user set balance = cast(balance as unsigned) + 5  where id = '"+s+"' and source = '0'");
            }
        }
        return ApiResponse.success();
    }

    /**
     * 给用户类型为 0 的用户生成邀请码
     */
    @GetMapping("/genCode")
    public ApiResponse<String> genCode(){
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.source, "0");
        condition.and().andIsNull(User.InnerColumn.invitedNumber.name());
        List<User> users = userService.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(users)){
            users.stream().forEach(user -> {
                boolean flag = true;
                while (flag ){
                    String shareCode = ShareCodeUtil.createShareCode();
                    List<User> userList = userService.findEq(User.InnerColumn.inviteNumber, shareCode);
                    if(CollectionUtils.isEmpty(userList)){
                        user.setInviteNumber(shareCode);
                        flag = false;
                    }
                }
                userService.update(user);
            });
        }
        return ApiResponse.success();
    }

    @GetMapping("/down")
    public ApiResponse<String> down() throws IOException {
        List<User> users = userService.findEq(User.InnerColumn.source, "1");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (StringUtils.isNotBlank(user.gethImg())) {
                URL u = new URL(user.gethImg());
                FileUtils.copyURLToFile(u, new File("D:/static/common/" + i + ".png"));
            }
        }
        return ApiResponse.success();
    }

    @GetMapping("/updateGmfs")
    public ApiResponse<String> updateGmfs(){
        SimpleCondition condition = new SimpleCondition(OrderList.class);
        condition.eq(OrderList.InnerColumn.proId, "622214058992467968");
        condition.eq(OrderList.InnerColumn.userid, "622355579720957952");
        condition.and().andNotEqualTo(OrderList.InnerColumn.num.name(), "10001568");
        List<OrderList> lists = listService.findByCondition(condition);
        List<String> collect = lists.stream().map(OrderList::getNum).collect(Collectors.toList());
        redis.delete("622214058992467968_nums");
        Collections.shuffle(collect);
        for (String num : collect) {
            ProInfoLuckNumBean numBean = new ProInfoLuckNumBean();
            numBean.setProId("622214058992467968");
            numBean.setProName("OPPO A5s 3GB RAM 32GB ROM");
            numBean.setLuckNum(num);
            redis.boundSetOps("622214058992467968" + "_nums").add(numBean);
        }
        for (OrderList list : lists) {
            listService.remove(list.getId());
        }
        return ApiResponse.success();
    }


}
