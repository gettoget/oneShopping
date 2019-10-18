package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.AwardMapper;
import com.ldz.biz.model.Award;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.AwardService;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class AwardServiceImpl extends BaseServiceImpl<Award, String> implements AwardService {

    @Autowired
    private AwardMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RechargeService rechargeService;


    @Override
    protected Mapper<Award> getBaseMapper() {
        return mapper;
    }

    @Override
    public ApiResponse<String> getAwardRecord() {
        return  ApiResponse.success("1");
//        String userId = getHeader("userId");
//        if(StringUtils.isBlank(userId)){
//            userId = getRequestParamterAsString("userId");
//        }
//        if(StringUtils.isBlank(userId)){
//            ApiResponse<String> res=  new ApiResponse<>();
//            res.setCode(999);
//            res.setMessage(MessageUtils.get("user.null"));
//            return res;
//        }
//        User user = userService.findById(userId);
//        if(user == null){
//            ApiResponse<String> res=  new ApiResponse<>();
//            res.setCode(999);
//            res.setMessage(MessageUtils.get("user.null"));
//            return res;
//        }
//        SimpleCondition condition = new SimpleCondition(Award.class);
//        condition.startWith(Award.InnerColumn.cjsj, DateTime.now().toString("yyyy-MM-dd"));
//        condition.eq(Award.InnerColumn.userId, userId);
//        List<Award> awards = findByCondition(condition);
//        return ApiResponse.success(awards.size() + "");
    }

    @Override
    public ApiResponse<String> validAndSave(Award entity) {
        String userId = getHeader("userId");
        if (StringUtils.isNotBlank(userId)) {
            entity.setUserId(userId);
        }
        // 查询进入是否已经抽奖
        SimpleCondition condition = new SimpleCondition(Award.class);
        condition.eq(Award.InnerColumn.userId, entity.getUserId());
        condition.startWith(Award.InnerColumn.cjsj, DateTime.now().toString("yyyy-MM-dd"));
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setStatus("0");
        save(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> saveLotty(String userId) {
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.idIsnull"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.notFind"));
        SimpleCondition condition = new SimpleCondition(Award.class);
        condition.eq(Award.InnerColumn.userId, userId);
        condition.startWith(Award.InnerColumn.cjsj, DateTime.now().toString("yyyy-MM-dd"));
        List<Award> awards = findByCondition(condition);
        RuntimeCheck.ifFalse(CollectionUtils.isEmpty(awards), "Hello, hari ini jumlah undian sudah habis");
        int[] arr = {0, 0, 2, 2, 10, 0, 0, 2, 2, 2};
        int anInt = RandomUtils.nextInt(0, 10);
        int i = 0;
        if (anInt <= 9) {
            i = arr[anInt];
        } else {
            i = 0;
        }
        // 抽中金币 生成中奖纪录
        Award a = new Award();
        a.setAwardName(i + "");
        a.setStatus("1");
        a.setCjsj(DateUtils.getNowTime());
        a.setId(genId());
        a.setUserId(userId);
        save(a);

        if (i > 0) {

            // 生成充值纪录
            Recharge recharge = new Recharge();
            recharge.setAmonut("" + i);
            recharge.setBz2("award");
            recharge.setCjsj(DateUtils.getNowTime());
            recharge.setCzjb("" + i);
            recharge.setCzqd("2");
            recharge.setCzqjbs(user.getBalance());
            recharge.setCzhjbs(Integer.parseInt(user.getBalance()) + i + "");
            recharge.setCzzt("2");
            recharge.setId(genId());
            recharge.setUserId(user.getId());
            recharge.setUsername(user.getUserName());
            rechargeService.save(recharge);
            userService.saveBalance(user.getId(), i + "");
        }

        return ApiResponse.success(i + "");

    }


}
