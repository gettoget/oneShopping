package com.ldz.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.UserMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.OrderListService;
import com.ldz.biz.service.OrderService;
import com.ldz.biz.service.ProInfoService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.symmetric.RC5;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.management.relation.RoleUnresolved;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author slu
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserMapper baseMapper;

    @Autowired
    private RedisTemplateUtil redis;

    @Autowired
    private ProInfoService proInfoService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListService orderListService;

    @Override
    protected Mapper<User> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public ApiResponse<String> register(String phone, String password, String password1, String code) {
        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        // 判断用户是否注册
        RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(users), MessageUtils.get("user.registered"));
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(password1, password), MessageUtils.get("user.pwdnotsame"));
        RuntimeCheck.ifBlank(code, MessageUtils.get("user.codeblank"));


        String regCode = (String) redis.boundValueOps(phone + "_register_code").get();
        RuntimeCheck.ifBlank(regCode, MessageUtils.get("user.regCodeBlank"));
        RuntimeCheck.ifTrue(StringUtils.equals(regCode, code), MessageUtils.get("user.regCodeError"));

        String imei = (String) getAttribute("imei");
        RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));

        // 保存用户
        String encryptUserPwd = EncryptUtil.encryptUserPwd(password);
        User user = new User();
        user.setId(genId());
        user.setBalance("0");
        user.setCjsj(DateUtils.getNowTime());
        user.setPhone(phone);
        user.setPwd(encryptUserPwd);
        user.setZjcs("0");
        user.setZt("0");
        user.setScore("0");
        user.setSource("0");
        user.setRegImei(imei);
        save(user);
        return ApiResponse.success(MessageUtils.get("user.regSuccess"));
    }

    @Override
    public ApiResponse<String> login(String phone, String password) {
        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.pwdblank"));
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.notregister"));
        String imei = (String) getAttribute("imei");
        RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
        User user = users.get(0);
        String userPwd = EncryptUtil.encryptUserPwd(password);
        RuntimeCheck.ifFalse(StringUtils.equals(user.getPwd(), userPwd), MessageUtils.get("user.pwderror"));

        // 用户登录成功后 生成token  保存token 和 用户信息  有效一天
        String token = JwtUtil.createToken(user.getId(), System.currentTimeMillis() + "");
        redis.boundValueOps(user.getId()).set(token, 1, TimeUnit.DAYS);
        redis.boundValueOps(user.getId() + "_userInfo").set(JSON.toJSON(user), 1, TimeUnit.DAYS);
        ApiResponse<String> res = new ApiResponse<>();
        res.setResult(token);
        res.setMessage(MessageUtils.get("user.loginSuccess"));
        user.setLastImei(imei);
        user.setLastTime(DateUtils.getNowTime());
        update(user);
        return res;
    }

    @Override
    public ApiResponse<String> editPwd(String phone, String pwd, String newPwd, String newPwd1) {
        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));
        RuntimeCheck.ifBlank(pwd, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifBlank(newPwd, MessageUtils.get("user.newPwdBlank"));
        RuntimeCheck.ifFalse(StringUtils.equals(newPwd1, newPwd), MessageUtils.get("user.pwdnotsame"));

        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.notregister"));
        User u = users.get(0);
        String userPwd = EncryptUtil.encryptUserPwd(pwd);
        RuntimeCheck.ifTrue(StringUtils.equals(userPwd, u.getPwd()), MessageUtils.get("user.pwderror"));

        u.setPwd(EncryptUtil.encryptUserPwd(newPwd));
        update(u);
        return ApiResponse.success(MessageUtils.get("user.editPwdSuc"));
    }

    @Override
    public ApiResponse<String> editUserInfo(User user) {
        User u = findById(user.getId());
        BeanUtil.copyProperties(user, u, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).setIgnoreProperties("id", "phone", "pwd", "source", "lastTime", "lastImei", "regImei", "balance", "cjsj", "refCode", "score", "zjcs"));
        update(u);
        redis.boundValueOps(u.getId() + "_userInfo").set(JSON.toJSON(user), 1, TimeUnit.DAYS);
        return ApiResponse.success(MessageUtils.get("user.editInfoSuc"));
    }

    @Override
    public ApiResponse<String> findPwd(String phone, String code, String newPwd, String newPwd1) {
        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.notregister"));
        User u = users.get(0);

        RuntimeCheck.ifBlank(code, MessageUtils.get("user.codeblank"));
        // 验证短信验证码
        String o = (String) redis.boundValueOps(phone + "_find_pwd").get();
        RuntimeCheck.ifBlank(o, MessageUtils.get("user.regCodeBlank"));
        RuntimeCheck.ifFalse(StringUtils.equals(o, code), MessageUtils.get("user.regCodeError"));


        RuntimeCheck.ifBlank(newPwd, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(newPwd1, newPwd), MessageUtils.get("user.pwdnotsame"));
        String encryptUserPwd = EncryptUtil.encryptUserPwd(newPwd);
        u.setPwd(encryptUserPwd);
        update(u);
        return ApiResponse.success(MessageUtils.get("user.editPwdSuc"));

    }

    @Override
    public ApiResponse<String> getMyWallet(int pageNum, int pageSize) {
        String userId = (String) getAttribute("userId");
        User user = findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
        PageInfo<PaymentBean> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            baseMapper.getMyPayRecord(userId);
        });
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> sendMsg(String phone, String type) {


        if (StringUtils.equals(type, "1")) {
            // 用户注册验证码
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.codePhoneBlank"));
            SimpleCondition condition = new SimpleCondition(User.class);
            condition.eq(User.InnerColumn.phone, phone);
            List<User> users = findByCondition(condition);
            RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.registered"));
            //todo 短信发送过程暂时忽略
            String code = SendSmsUtil.sendMSG(phone, type);
            redis.boundValueOps(phone + "_register_code").set(code, 5, TimeUnit.MINUTES);
        } else if (StringUtils.equals(type, "2")) {
            // 用户找回密码 验证码
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.codePhoneBlank"));

            SimpleCondition condition = new SimpleCondition(User.class);
            condition.eq(User.InnerColumn.phone, phone);
            List<User> users = findByCondition(condition);
            RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.notregister"));

            //todo 短信发送过程暂时忽略
            String code = SendSmsUtil.sendMSG(phone, type);
            redis.boundValueOps(phone + "_find_pwd").set(code, 5, TimeUnit.MINUTES);
        } else if (StringUtils.equals(type, "3")) {
            // 找回支付密码不需要填手机号
            String userId = (String) getAttribute("userId");
            User user = findById(userId);
            String code = SendSmsUtil.sendMSG(user.getPhone(), type);
            // 存储验证码
            redis.boundValueOps(user.getPhone() + "_find_pay_pwd").set(code, 5, TimeUnit.MINUTES);
        } else {
            return ApiResponse.fail(MessageUtils.get("user.typeError"));
        }
        return ApiResponse.success(MessageUtils.get("user.sendMsgSuc"));
    }

    @Override
    public ApiResponse<List<String>> myNum(String id) {
        List<String> nums = new ArrayList<>();

        String userId = (String) getAttribute("userId");
        ProInfo proInfo = proInfoService.findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));

        SimpleCondition condition = new SimpleCondition(OrderList.class);
        condition.eq(OrderList.InnerColumn.userid, userId);
        condition.eq(OrderList.InnerColumn.proId, id);
        List<OrderList> orderLists = orderListService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(orderLists)) {
            List<String> collect = orderLists.stream().map(OrderList::getNum).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            return ApiResponse.success(collect);
        }
        return ApiResponse.success(nums);
    }

    @Override
    public ApiResponse<String> savePayPwd(String pwd, String pwd1) {
        RuntimeCheck.ifBlank(pwd, MessageUtils.get("user.paypwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(pwd, pwd1), MessageUtils.get("user.pwdnotsame"));
        String userId = (String) getAttribute("userId");
        User user = findById(userId);
        String userPwd = EncryptUtil.encryptUserPwd(pwd);
        user.setPayPwd(userPwd);
        update(user);
        return ApiResponse.success(MessageUtils.get("message.setSuc"));
    }

    @Override
    public ApiResponse<String> findPayPwd(String newPwd, String newPwd1, String code) {
        RuntimeCheck.ifBlank(newPwd,MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(newPwd1,newPwd), MessageUtils.get("user.pwdnotsame"));
        String userId = (String) getAttribute("userId");
        User user = findById(userId);
        String code1 = (String) redis.boundValueOps(user.getPhone() + "_find_pay_pwd").get();
        RuntimeCheck.ifBlank(code1, MessageUtils.get("user.regCodeBlank"));
        RuntimeCheck.ifFalse(StringUtils.equals(code,code1), MessageUtils.get("user.regCodeError"));
        String userPwd = EncryptUtil.encryptUserPwd(newPwd);
        user.setPayPwd(userPwd);
        update(user);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> genRefferCode() {
        String userId = (String) getAttribute("userId");
        User user = findById(userId);




        return null;
    }









	/*@Override
	public ApiResponse<String> sendPayPwdMsg() {
		String userId = (String) getAttribute("userId");
		return null;
	}
*/

}