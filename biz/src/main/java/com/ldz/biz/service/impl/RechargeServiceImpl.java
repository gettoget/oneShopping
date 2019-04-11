package com.ldz.biz.service.impl;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.RechargeService;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.Recharge;

@Service
public class RechargeServiceImpl extends BaseServiceImpl<Recharge, String> implements RechargeService {

	@Autowired
	private RechargeMapper baseMapper;
	@Autowired
	private UserService userService;


	@Override
	protected Mapper<Recharge> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> recharge(int amount) {

		RuntimeCheck.ifTrue( amount <= 0 , MessageUtils.get("recharge.amountLessZero"));

		String userId = (String) getAttribute("userId");
		User user = userService.findById(userId);
		RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));



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
		String imei = (String) getAttribute("imei");
		RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
		user.setBalance(Integer.parseInt(user.getBalance()) + amount + "");
		recharge.setCzhjbs(user.getBalance());
		int i = save(recharge);
		if(i == 1){
			userService.update(user);
		}

		return ApiResponse.success(MessageUtils.get("recharge.reSuc"));
    }
}