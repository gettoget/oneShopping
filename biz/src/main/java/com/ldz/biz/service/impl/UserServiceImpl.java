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
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author slu
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {


    @Value("${filePath}")
    private String filePath;
    @Autowired
    private UserMapper baseMapper;

    @Autowired
    private RedisTemplateUtil redis;

    @Autowired
    private StringRedisTemplate redisDao;

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
    public void afterPager(PageInfo<User> info) {
        List<User> list = info.getList();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Set<String> collect = list.stream().map(User::getId).collect(Collectors.toSet());
        List<UserInModel> map = baseMapper.sumCharge(collect);

        Map<String, UserInModel> modelMap = map.stream().collect(Collectors.toMap(UserInModel::getUserId, p -> p));
        list.forEach(user -> {
            if(modelMap.containsKey(user.getId())){
                UserInModel inModel = modelMap.get(user.getId());
                user.setCy(inModel.getCys());
                user.setCz(inModel.getCz());
                user.setXf(inModel.getXf());
            }else{
                user.setXf("0");
                user.setCy("0");
                user.setCz("0");
            }
            user.setPayPwd("");
            user.setPwd("");

        });
    }


    @Override
    public ApiResponse<Map<String, Object>> register(String phone, String password, String password1, String code, String username) {
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifBlank(password1, MessageUtils.get("user.pwdBlank"));
        password = checkPer(password);
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.timeError"));
        password1 = checkPer(password1);
        RuntimeCheck.ifBlank(password1, MessageUtils.get("user.timeError"));

        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));

        RuntimeCheck.ifFalse(StringUtils.equals(password, password1), MessageUtils.get("user.pwdnotsame"));
        RuntimeCheck.ifBlank(code, MessageUtils.get("user.codeblank"));
        RuntimeCheck.ifBlank(username, MessageUtils.get("user.nameIsBlank"));
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        // 判断用户是否注册
        RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(users), MessageUtils.get("user.registered"));

        String regCode = (String) redis.boundValueOps(phone + "_register_code").get();
        RuntimeCheck.ifBlank(regCode, MessageUtils.get("user.regCodeBlank"));
        RuntimeCheck.ifFalse(StringUtils.equals(regCode, code), MessageUtils.get("user.regCodeError"));

        String imei = getHeader("imei");
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
        user.setUserName(username);
        user.setLastImei(imei);
        user.setLastTime(DateUtils.getNowTime());
        save(user);
        String token = JwtUtil.createToken(user.getId(), System.currentTimeMillis() + "");
        redisDao.boundValueOps(user.getId()).set(token, 30, TimeUnit.DAYS);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setMessage(MessageUtils.get("user.regSuccess"));
        UserModel model = new UserModel(user);
        model.setToken(token);
        redis.boundValueOps(user.getId() + "_userInfo").set(JSON.toJSON(model), 30, TimeUnit.DAYS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", model);
        response.setResult(map);
        return response;
    }

    @Override
    public ApiResponse<Map<String, Object>> login(String phone, String password) throws Exception {
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.pwdblank"));
        password = checkPer(password);
        RuntimeCheck.ifBlank(password, MessageUtils.get("user.timeError"));
        RuntimeCheck.ifBlank(phone, MessageUtils.get("user.phoneblank"));
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.phone, phone);
        List<User> users = findByCondition(condition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("user.notregister"));
        String imei = getHeader("imei");
        RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
        User user = users.get(0);
        RuntimeCheck.ifFalse(user.getZt().equals("0"), MessageUtils.get("user.isLocked"));
        String userPwd = EncryptUtil.encryptUserPwd(password);
        RuntimeCheck.ifFalse(StringUtils.equals(user.getPwd(), userPwd), MessageUtils.get("user.pwderror"));

        // 用户登录成功后 生成token  保存token 和 用户信息  有效一天
        String token = JwtUtil.createToken(user.getId(), System.currentTimeMillis() + "");
        redisDao.boundValueOps(user.getId()).set(token, 30, TimeUnit.DAYS);

        ApiResponse<Map<String, Object>> res = new ApiResponse<>();
        res.setMessage(MessageUtils.get("user.loginSuccess"));
        user.setLastImei(imei);
        user.setLastTime(DateUtils.getNowTime());
        update(user);
        UserModel model = new UserModel(user);
        model.setToken(token);
        redis.boundValueOps(user.getId() + "_userInfo").set(JSON.toJSON(model), 30, TimeUnit.DAYS);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("userInfo", model);
        res.setResult(tokenMap);
        return res;
    }

    @Override
    public ApiResponse<String> editPwd(String pwd, String newPwd, String newPwd1) {
        RuntimeCheck.ifBlank(pwd, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifBlank(newPwd, MessageUtils.get("user.newPwdBlank"));
        pwd = checkPer(pwd);
        RuntimeCheck.ifBlank(pwd, MessageUtils.get("user.timeError"));
        newPwd = checkPer(newPwd);
        RuntimeCheck.ifBlank(newPwd, MessageUtils.get("user.timeError"));
        newPwd1 = checkPer(newPwd1);
        RuntimeCheck.ifBlank(newPwd1, MessageUtils.get("user.timeError"));


        RuntimeCheck.ifFalse(StringUtils.equals(newPwd, newPwd1), MessageUtils.get("user.pwdnotsame"));
        RuntimeCheck.ifTrue(StringUtils.equals(newPwd, pwd), MessageUtils.get("user.pwdSameToNew"));
        String userId = getAttributeAsString("userId");
        User u = findById(userId);
        RuntimeCheck.ifTrue(u == null, MessageUtils.get("user.notregister"));

        String userPwd = EncryptUtil.encryptUserPwd(pwd);
        RuntimeCheck.ifFalse(StringUtils.equals(userPwd, u.getPwd()), MessageUtils.get("user.pwderror"));

        u.setPwd(EncryptUtil.encryptUserPwd(newPwd));
        update(u);
        return ApiResponse.success(MessageUtils.get("user.editPwdSuc"));
    }

    @Override
    public ApiResponse<UserModel> editUserInfo(User user) {
        ApiResponse<UserModel> res = new ApiResponse<>();
        String userId = getAttributeAsString("userId");
        User u = findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
        BeanUtil.copyProperties(user, u, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).setIgnoreProperties("id", "phone", "pwd", "source", "lastTime", "lastImei", "regImei", "balance", "cjsj", "refCode", "score", "zjcs"));
        update(u);
        UserModel model = new UserModel(u);
//        redis.boundValueOps(u.getId() + "_userInfo").set(JSON.toJSON(model), 30, TimeUnit.DAYS);
        res.setMessage(MessageUtils.get("user.editInfoSuc"));
        res.setResult(model);
        return res;
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
    public PageResponse<PaymentBean> getMyWallet(int pageNum, int pageSize) {
        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
        PageInfo<PaymentBean> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            baseMapper.getMyPayRecord(userId);
        });
        PageResponse<PaymentBean> res = new PageResponse<>();
        res.setList(info.getList());
        res.setPageNum(pageNum);
        res.setPageSize(pageSize);
        res.setTotal(info.getTotal());
        return res;
    }

    @Override
    public ApiResponse<String> sendMsg(String phone, String type) {


        if (StringUtils.equals(type, "1")) {
            // 用户注册验证码
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.codePhoneBlank"));
            phone = checkPer(phone);
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.timeError"));

            SimpleCondition condition = new SimpleCondition(User.class);
            condition.eq(User.InnerColumn.phone, phone);
            List<User> users = findByCondition(condition);
            RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(users), MessageUtils.get("user.registered"));
            //todo 短信发送过程暂时忽略
            String code = SendSmsUtil.sendMSG(phone, type);
            redis.boundValueOps(phone + "_register_code").set(code, 5, TimeUnit.MINUTES);
        } else if (StringUtils.equals(type, "2")) {
            // 用户找回密码 验证码
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.codePhoneBlank"));
            phone = checkPer(phone);
            RuntimeCheck.ifBlank(phone, MessageUtils.get("user.timeError"));
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
            RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
            User user = findById(userId);
            String code = SendSmsUtil.sendMSG(user.getPhone(), type);
            // 存储验证码
            redis.boundValueOps(user.getPhone() + "_find_pay_pwd").set(code, 5, TimeUnit.MINUTES);
        } else if (StringUtils.equals(type, "4")) {
            // 找回支付密码不需要填手机号
            String userId = (String) getAttribute("userId");
            RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
            User user = findById(userId);
            String code = SendSmsUtil.sendMSG(user.getPhone(), type);
            // 存储验证码
            redis.boundValueOps(user.getPhone() + "_pay").set(code, 5, TimeUnit.MINUTES);
        } else {
            return ApiResponse.fail(MessageUtils.get("user.typeError"));
        }
        return ApiResponse.success(MessageUtils.get("user.sendMsgSuc"));
    }

    @Override
    public ApiResponse<List<String>> myNum(String id) {
        List<String> nums = new ArrayList<>();

        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        ProInfo proInfo = proInfoService.findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));
        if (StringUtils.isNotBlank(userId)) {
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.userid, userId);
            condition.eq(OrderList.InnerColumn.proId, id);
            List<OrderList> orderLists = orderListService.findByCondition(condition);
            if (CollectionUtils.isNotEmpty(orderLists)) {
                List<String> collect = orderLists.stream().map(OrderList::getNum).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                return ApiResponse.success(collect);
            }
        }

        return ApiResponse.success(nums);
    }

    @Override
    public ApiResponse<String> savePayPwd(String pwd, String pwd1) {
        RuntimeCheck.ifBlank(pwd, MessageUtils.get("user.paypwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(pwd, pwd1), MessageUtils.get("user.pwdnotsame"));
        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = findById(userId);
        RuntimeCheck.ifTrue(StringUtils.isNotBlank(user.getPayPwd()), MessageUtils.get("user.paypwdIsNotNull"));
        String userPwd = EncryptUtil.encryptUserPwd(pwd);
        user.setPayPwd(userPwd);
        update(user);
        return ApiResponse.success(MessageUtils.get("message.setSuc"));
    }

    @Override
    public ApiResponse<String> findPayPwd(String newPwd, String newPwd1, String code) {
        RuntimeCheck.ifBlank(newPwd, MessageUtils.get("user.pwdblank"));
        RuntimeCheck.ifFalse(StringUtils.equals(newPwd1, newPwd), MessageUtils.get("user.pwdnotsame"));
        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = findById(userId);
        String code1 = (String) redis.boundValueOps(user.getPhone() + "_find_pay_pwd").get();
        RuntimeCheck.ifBlank(code1, MessageUtils.get("user.regCodeBlank"));
        RuntimeCheck.ifFalse(StringUtils.equals(code, code1), MessageUtils.get("user.regCodeError"));
        String userPwd = EncryptUtil.encryptUserPwd(newPwd);
        user.setPayPwd(userPwd);
        update(user);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> genRefferCode() {
        String userId = (String) getAttribute("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = findById(userId);
        if (StringUtils.isBlank(user.getRefCode())) {
            long id = Long.parseLong(user.getId());
            long l = id / (32 * 32 * 32 * 32);
            String refferCode = Long.toHexString(l).toUpperCase();
            user.setRefCode(refferCode);
            update(user);
        }

        return ApiResponse.success(user.getRefCode());
    }

    @Override
    public ApiResponse<UserModel> getUserInfo() {
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user = findById(userId);
        UserModel userModel = new UserModel(user);
        return ApiResponse.success(userModel);
    }

    @Override
    public void initRobot() {
        redis.delete(User.class.getName());
        SimpleCondition condition = new SimpleCondition(User.class);
        condition.eq(User.InnerColumn.source, "1");
        condition.eq(User.InnerColumn.zt, "0");
        List<User> users = findByCondition(condition);
        users.stream().forEach(user -> redis.boundSetOps(User.class.getName()).add(user));
    }

    @Override
    public ApiResponse<String> updateEntity(User entity) {
        RuntimeCheck.ifBlank(entity.getId(), MessageUtils.get("user.idIsnull"));
        User user = findById(entity.getId());
        RuntimeCheck.ifNull(user, MessageUtils.get("user.notFind"));
        BeanUtil.copyProperties(entity, user, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("id", "payPwd", "pwd", "zjcs", "score", "balance"));
        update(user);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> saveChannelId(String channelId) {
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        redis.boundValueOps(userId + "_channelId").set(channelId);
        return ApiResponse.success();
    }


    private String checkPer(String secret) {
        String s;
        try {
            s = RSAUtils.decryptWithRSA(secret);
            String[] split = s.split(";");
            RuntimeCheck.ifTrue(split.length != 2, MessageUtils.get("user.timeError"));
            String[] times = split[1].split(":");
            RuntimeCheck.ifTrue(times.length != 2, MessageUtils.get("user.timeError"));
            long time = Long.parseLong(times[1]);
            DateTime dateTime = new DateTime(time);
            RuntimeCheck.ifTrue(dateTime.plusMinutes(10).compareTo(DateTime.now()) < 0, MessageUtils.get("user.timeError"));
            return split[0];
        } catch (Exception e) {
            RuntimeCheck.ifTrue(true, MessageUtils.get("user.timeError"));
        }
        return "";
    }


}