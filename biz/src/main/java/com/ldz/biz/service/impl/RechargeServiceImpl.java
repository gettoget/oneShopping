package com.ldz.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BankMapper;
import com.ldz.biz.mapper.BankRecordMapper;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.Bank;
import com.ldz.biz.model.BankRecord;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.BankService;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.AndroidMsgBean;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RechargeServiceImpl extends BaseServiceImpl<Recharge, String> implements RechargeService {

    @Autowired
    private RechargeMapper baseMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplateUtil redis;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BankService bankService;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private BankRecordMapper bankRecordMapper;
    @Value("${czqr}")
    private int czqr;

    @Value("${mall_id}")
    private String mallId;

    @Value("${shared_key}")
    private String sharedKey;

    @Value("${ratio}")
    private int ratio;

    @Override
    protected Mapper<Recharge> getBaseMapper() {
        return baseMapper;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition) {

        String username = getRequestParamterAsString("username");
        if (StringUtils.isNotBlank(username)) {
            List<User> users = userService.findLike(User.InnerColumn.userName, username);
            if (CollectionUtils.isEmpty(users)) {
                return false;
            }
            Set<String> userid = users.stream().map(User::getId).collect(Collectors.toSet());
            condition.in(Recharge.InnerColumn.userId, userid);
        }
        String type = getRequestParamterAsString("type");
        if(StringUtils.equals("ovo", type)){
            List<Bank> banks = bankService.findAll();
            if(CollectionUtils.isNotEmpty(banks)){
                List<String> collect = banks.stream().map(Bank::getBankNo).collect(Collectors.toList());
                condition.in(Recharge.InnerColumn.bz1, collect);
            }else{
                return false;
            }
        }
        return true;
    }

    @Override
    public ApiResponse<String> saveRecharge(int amount) {

        String paymentId = getRequestParamterAsString("paymentId");
        log.info("请求参数 : {} , {}", amount, paymentId);
        RuntimeCheck.ifBlank(paymentId, MessageUtils.get("order.paymentError"));
        RuntimeCheck.ifTrue(amount <= 0, MessageUtils.get("recharge.amountLessZero"));
        int payAmount = amount;
        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));

        String imei = (String) getAttribute("imei");

        Object o = redis.boundValueOps(imei + "recharge").get();
        if (o != null) {
            return ApiResponse.fail(MessageUtils.get("FrequentOperation"));
        } else {
            redis.boundValueOps(imei + "recharge").set(1, 10, TimeUnit.SECONDS);
        }

        if (StringUtils.isBlank(user.getBalance())) {
            user.setBalance("0");
        }
        amount = amount / ratio;
        RuntimeCheck.ifFalse(amount >= 20, "Tolong upgrade app!");
        // 判断是否为首次充值 , 首次充值 加  50%
        SimpleCondition condition = new SimpleCondition(Recharge.class);
        condition.eq(Recharge.InnerColumn.userId, user.getId());
        condition.eq(Recharge.InnerColumn.czqd, "1");
        condition.eq(Recharge.InnerColumn.czzt, "2");
        List<Recharge> recharges = findByCondition(condition);
        int ysje =amount;

        if(CollectionUtils.isEmpty(recharges)){
            amount = (int) (amount * 1.5);
        }

        Recharge recharge = new Recharge();
        recharge.setId(genId());
        recharge.setAmonut(ysje + "");
        recharge.setCzjb(amount + "");
        recharge.setCjsj(DateUtils.getNowTime());
        recharge.setCzzt("1");
        recharge.setCzqd("1");
        recharge.setUserId(userId);
        ApiResponse<String> res = new ApiResponse<>();
        if(StringUtils.equals(paymentId, "ovo")){
            // 获取卡号
            Bank bank = bankMapper.getByType(paymentId, DateTime.now().toString("yyyy-MM-dd"), czqr);
            RuntimeCheck.ifNull(bank , MessageUtils.get("defeat"));
            recharge.setBz1(bank.getBankNo());
            res.setResult( bank.getBankNo() + "," + recharge.getId());
        }else{
            recharge.setBz1(paymentId);
            recharge.setBz2("recharge");
            RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("mall_id", mallId);
            paramsMap.put("amount", payAmount + "");
            paramsMap.put("trans_id", recharge.getId());
            paramsMap.put("payment_id", paymentId);
            String words = DigestUtils.sha1Hex(mallId + sharedKey + payAmount + recharge.getId());
            paramsMap.put("words", words);
            log.info(" 支付请求参数 : {}", JSON.toJSON(paramsMap));
            // 存储充值时的报文
            recharge.setCzbw(JSON.toJSONString(paramsMap));
            String post = HttpUtil.post("https://pay.gokado.id/payment/generate-pay-code", paramsMap);
            JSONObject object = JSON.parseObject(post);
            Integer code = object.getInteger("code");
            Integer status = object.getInteger("status");
            RuntimeCheck.ifFalse(code == 0 && status == 200, MessageUtils.get("order.error"));
            String data = object.getString("data");
            res.setResult(data + "," + recharge.getId());
        }

        // 充值前金币和充值后金币不在充值的时候写 , 写在充值回调的时候
//        String balance = Integer.parseInt(user.getBalance()) + amount + "";
        recharge.setImei(imei);
        int i = save(recharge);
        res.setMessage(MessageUtils.get("recharge.orderSuc"));
        return res;
    }

    @Override
    public PageResponse<Recharge> getNewPager(Page<Recharge> page) {
        PageResponse<Recharge> res = new PageResponse<>();
        String userId = getAttributeAsString("userId");
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
            LimitedCondition condition = getQueryCondition();
            condition.eq(Recharge.InnerColumn.userId, userId);
            condition.eq(Recharge.InnerColumn.czzt, "2");
            PageInfo<Recharge> info = findPage(page, condition);
            res.setTotal(info.getTotal());
            res.setList(info.getList());
            res.setPageSize(page.getPageSize());
            res.setPageNum(page.getPageNum());
        }
        return res;
    }

    @Override
    public ApiResponse<String> paySuc(String amount, String trans_id, String words, String data) {
        RuntimeCheck.ifBlank(amount, " AMOUNT IS NULL");
        RuntimeCheck.ifBlank(trans_id, "TRAN_ID IS NULL");
        RuntimeCheck.ifBlank(words, "WORDS IS NULL");
        RuntimeCheck.ifBlank(data, "DATA IS NULL");
        log.info("支付回调接口: {} , {} , {} , {}", amount, trans_id, words, data);
        Recharge recharge = findById(trans_id);
        if (recharge == null) {
            log.info(" 订单id 异常 ,  {} ", trans_id);
            return ApiResponse.fail("TRANS_ID IS ERROR");
        }
        if (StringUtils.equals(recharge.getCzzt(), "2")) {
            log.info("订单已经支付成功");
            return ApiResponse.success("SUCCESS");
        }

        String hex = DigestUtils.sha1Hex(mallId + sharedKey + amount + trans_id);
        recharge.setQrsj(DateUtils.getNowTime());
        recharge.setQrbw(data);
        if (StringUtils.equals(words, hex)) {
            User user = userService.findById(recharge.getUserId());
            userService.saveBalance(recharge.getUserId(), Integer.parseInt(recharge.getCzjb())  + "");
            recharge.setCzzt("2");
            recharge.setCzqjbs(user.getBalance());
            recharge.setCzhjbs(Integer.parseInt(user.getBalance()) +Integer.parseInt(recharge.getCzjb())  + "" );
            update(recharge);
            String channelId = (String) redis.boundValueOps(recharge.getUserId() + "_channelId").get();
            if (StringUtils.isNotBlank(channelId)) {
                AndroidMsgBean msgBean = new AndroidMsgBean();
                msgBean.setType("7");
                msgBean.setJson(JsonUtil.toJson(recharge));
                BaiduPushUtils.pushSingleMsg(channelId, 0, JsonUtil.toJson(msgBean), 3);
            }

            return ApiResponse.success("SUCCESS");
        } else {
            recharge.setCzzt("3");
            update(recharge);
            String channelId = (String) redis.boundValueOps(recharge.getUserId() + "_channelId").get();
            if (StringUtils.isNotBlank(channelId)) {
                AndroidMsgBean msgBean = new AndroidMsgBean();
                msgBean.setType("7");
                msgBean.setJson(JsonUtil.toJson(recharge));
                BaiduPushUtils.pushSingleMsg(channelId, 0, JsonUtil.toJson(msgBean), 3);
            }
            return ApiResponse.fail("WORDS NOT MATCH");
        }

    }

    @Override
    public ApiResponse<List<Map>> getPaymentChannel() {

        String s = HttpUtil.get("https://pay.gokado.id/payment/get-pay-list");
        JSONObject object = JSON.parseObject(s);
        Integer code = object.getInteger("code");
        Integer status = object.getInteger("status");
        RuntimeCheck.ifFalse(code == 0 && status == 200, MessageUtils.get("order.payChannelError"));

        JSONArray data = object.getJSONArray("data");
        List<Map> maps = data.toJavaList(Map.class);
        return ApiResponse.success(maps);
    }

    @Override
    public ApiResponse<String> checkPayment(String id) {
        RuntimeCheck.ifBlank(id, MessageUtils.get("order.idBlank"));
        Recharge recharge = findById(id);
        RuntimeCheck.ifNull(recharge, MessageUtils.get("order.notTrue"));
        return ApiResponse.success(recharge.getCzzt());
    }

    @Override
    public ApiResponse<String> paySucTest(String id) {
        RuntimeCheck.ifBlank(id, MessageUtils.get("order.idBlank"));
        Recharge recharge = findById(id);
        RuntimeCheck.ifNull(recharge, MessageUtils.get("order.notTrue"));
        if (StringUtils.equals(recharge.getCzzt(), "2")) {
            return ApiResponse.success("SUCCESS");
        }
        User user = userService.findById(recharge.getUserId());
        userService.saveBalance(recharge.getUserId(),
                Integer.parseInt(recharge.getCzjb()) + "");
        recharge.setCzzt("2");
        recharge.setQrsj(DateUtils.getNowTime());
        recharge.setCzqjbs(user.getBalance());
        recharge.setCzhjbs(Integer.parseInt(user.getBalance()) + Integer.parseInt(recharge.getCzjb()) + "");
        // 存储银行卡号
        BankRecord bankRecord = new BankRecord();
        bankRecord.setId(genId());
        bankRecord.setCjsj(DateUtils.getNowTime());
        bankRecord.setBankNo(recharge.getBz1());
        bankRecordMapper.insertSelective(bankRecord);
        update(recharge);
        String channelId = (String) redis.boundValueOps(recharge.getUserId() + "_channelId").get();
        if (StringUtils.isNotBlank(channelId)) {
            AndroidMsgBean msgBean = new AndroidMsgBean();
            msgBean.setType("7");
            msgBean.setJson(JsonUtil.toJson(recharge));
            BaiduPushUtils.pushSingleMsg(channelId, 0, JsonUtil.toJson(msgBean), 3);
        }
        return ApiResponse.success("SUCCESS");
    }

    @Override
    public ApiResponse<String> getRechargeNum() {
        String userId = getHeader("userId");
        SimpleCondition simpleCondition = new SimpleCondition(Recharge.class);
        simpleCondition.eq(Recharge.InnerColumn.userId, userId);
        simpleCondition.eq(Recharge.InnerColumn.czqd, "1");
        simpleCondition.eq(Recharge.InnerColumn.czzt, "2");
        List<Recharge> list = findByCondition(simpleCondition);
        int size = list.size();
        return ApiResponse.success(size+ "");
    }

    @Override
    public ApiResponse<Map<String, String>> getMoreThanTwo() {

        Map<String, String> two = baseMapper.getMoreThanTwo();


        return ApiResponse.success(two);
    }

    @Override
    public ApiResponse<String> saveHighPraise() {
        // 获取用户 id
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.notFind"));
        // 用户余额加奖励金币 生成充值记录
        userMapper.saveBalance(userId,  "" + 2);
        Recharge recharge = new Recharge();
        recharge.setAmonut("2");
        recharge.setCzzt("2");
        recharge.setCjsj(DateUtils.getNowTime());
        recharge.setCzjb("2");
        recharge.setCzqd("2");
        recharge.setUserId(user.getId());
        recharge.setCzqjbs(user.getBalance());
        recharge.setCzhjbs(Integer.parseInt(user.getBalance()) + 2 + "");
        recharge.setBz2("praise");
        recharge.setId(genId());
        save(recharge);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Integer> getPraise() {
        // 查看用户是否有评分奖励 , 如果有 则表示没有打分
        // 获取用户 id
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.notFind"));
        SimpleCondition simpleCondition = new SimpleCondition(Recharge.class);
        simpleCondition.eq(Recharge.InnerColumn.userId, userId);
        simpleCondition.eq(Recharge.InnerColumn.bz2, "praise");
        List<Recharge> recharges = findByCondition(simpleCondition);
        return ApiResponse.success(recharges.size());
    }

    @Override
    public ApiResponse<String> returnGcoin(String id) {
        // 获取超过购买达到 5 个币的用户 , 返还金币
        List<User> users = baseMapper.getMoreThanFive(id);
        for (User user : users) {
            if(!StringUtils.equals(user.getId(), "611629732441620480")){
                continue;
            }
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
            userMapper.saveBalance(user.getId(),  "" + jb);
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
            recharge.setId(genId());
            save(recharge);
            // 短信发送
            String s = SendSmsUtil.sendMSG(user.getPhone(), "produk yang anda ikuti sudah berakhir, selamat, anda berhak mendapatkan bonus " + jb + " Gcoin");
            System.out.println(s);
        }
        return ApiResponse.success();
    }


    @Override
    public void afterPager(PageInfo<Recharge> info) {
        List<Recharge> list = info.getList();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Set<String> set = list.stream().map(Recharge::getUserId).collect(Collectors.toSet());
        List<User> users = userService.findByIds(set);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
        list.forEach(recharge -> {
            if (userMap.containsKey(recharge.getUserId())) {
                User user = userMap.get(recharge.getUserId());
                recharge.setUsername(user.getUserName());
                recharge.setHimg(user.gethImg());
            }
        });
    }

}