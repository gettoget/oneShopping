package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.RechargeService;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.Recharge;

import java.util.concurrent.TimeUnit;

@Service
public class RechargeServiceImpl extends BaseServiceImpl<Recharge, String> implements RechargeService {

	@Autowired
	private RechargeMapper baseMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplateUtil redis;

	@Override
	protected Mapper<Recharge> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> saveRecharge(int amount) {

		RuntimeCheck.ifTrue( amount <= 0 , MessageUtils.get("recharge.amountLessZero"));

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


		// todo 充值接口调用


		if(StringUtils.isBlank(user.getBalance())){
			user.setBalance("0");
		}

		Recharge recharge = new Recharge();
		recharge.setId(genId());
		recharge.setAmonut(amount+"");
		recharge.setCjsj(DateUtils.getNowTime());
		recharge.setCzzt("1");
		recharge.setCzqjbs(user.getBalance());
		recharge.setCzqd("1");
		recharge.setUserId(userId);
		RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
		user.setBalance(Integer.parseInt(user.getBalance()) + amount + "");
		recharge.setCzhjbs(user.getBalance());
		recharge.setImei(imei);
		int i = save(recharge);
		if(i == 1){
			userService.update(user);
		}

		return ApiResponse.success(MessageUtils.get("recharge.reSuc"));
    }

	@Override
	public PageResponse<Recharge> getNewPager(Page<Recharge> page) {
		PageResponse<Recharge> res = new PageResponse<>();
		String userId = getAttributeAsString("userId");
		if(StringUtils.isNotBlank(userId)){
			User user = userService.findById(userId);
			RuntimeCheck.ifNull(user,MessageUtils.get("user.null"));
			LimitedCondition condition = getQueryCondition();
			condition.eq(Recharge.InnerColumn.userId, userId);
			PageInfo<Recharge> info = findPage(page, condition);
			res.setTotal(info.getTotal());
			res.setList(info.getList());
			res.setPageSize(page.getPageSize());
			res.setPageNum(page.getPageNum());
		}
		return res;
	}
}