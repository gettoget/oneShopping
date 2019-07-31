package com.ldz.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.RechargeMapper;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.RechargeService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class RechargeServiceImpl extends BaseServiceImpl<Recharge, String> implements RechargeService {

	@Autowired
	private RechargeMapper baseMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplateUtil redis;

	@Value("${mall_id}")
	private String mallId;

	@Value("${shared_key}")
	private String sharedKey;

	@Override
	protected Mapper<Recharge> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public boolean fillPagerCondition(LimitedCondition condition){

		String username = getRequestParamterAsString("username");
		if(StringUtils.isNotBlank(username)){
			List<User> users = userService.findLike(User.InnerColumn.userName, username);
			if(CollectionUtils.isEmpty(users)){
				return false;
			}
			Set<String> userid = users.stream().map(User::getId).collect(Collectors.toSet());
			condition.in(Recharge.InnerColumn.userId, userid);
		}
		return true;
	}

    @Override
    public ApiResponse<String> saveRecharge(int amount) {

		String paymentId = getRequestParamterAsString("paymentId");
		RuntimeCheck.ifBlank(paymentId, MessageUtils.get("order.paymentError"));
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

		if(StringUtils.isBlank(user.getBalance())){
			user.setBalance("0");
		}

		Recharge recharge = new Recharge();
		recharge.setId(genId());
		recharge.setAmonut(amount+"");
		recharge.setCzjb(amount + "");
		recharge.setCjsj(DateUtils.getNowTime());
		recharge.setCzzt("1");
		recharge.setCzqjbs(user.getBalance());
		recharge.setCzqd("1");
		recharge.setUserId(userId);
		RuntimeCheck.ifBlank(imei, MessageUtils.get("user.imeiBlank"));
		String balance = Integer.parseInt(user.getBalance()) + amount + "";
		recharge.setCzhjbs(balance);
		recharge.setImei(imei);
		int i = save(recharge);

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("mall_id",mallId);
		paramsMap.put("amount",amount+"");
		paramsMap.put("trans_id",recharge.getId());
		paramsMap.put("payment_id",paymentId);
		String words = DigestUtils.sha1Hex(mallId + sharedKey + amount + recharge.getId());
		paramsMap.put("words",words);
		String post = HttpUtil.post("https://pay.gokado.id/payment/generate-pay-code", paramsMap);
		JSONObject object = JSON.parseObject(post);
		Integer code = object.getInteger("code");
		Integer status = object.getInteger("status");
		RuntimeCheck.ifFalse(code == 0 && status == 200, MessageUtils.get("order.error"));
		String data = object.getString("data");

		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(data);
		res.setMessage(MessageUtils.get("recharge.orderSuc"));
		return res;
    }

	public static void main(String[] args) {
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("mall_id","cabf5fdd363bbb73209a32d4ce12b65a");
		paramsMap.put("amount",1+"");
		paramsMap.put("trans_id","1");
		paramsMap.put("payment_id","11111029");
		String words = DigestUtils.sha1Hex("cabf5fdd363bbb73209a32d4ce12b65a" + "t8oO2DMFX8UY" + "1" + "1");
		paramsMap.put("words",words);
		String post = HttpUtil.post("https://pay.gokado.id/payment/generate-pay-code", paramsMap);
		System.out.println(post);
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

	@Override
	public String paySuc(String amount, String trans_id, String words) {
		Recharge recharge = findById(trans_id);
		String hex = DigestUtils.sha1Hex(mallId + sharedKey + trans_id + recharge.getAmonut());
		if (StringUtils.equals(words,hex)) {
			User user = userService.findById(recharge.getUserId());
			user.setBalance(recharge.getCzhjbs());
			userService.update(user);
			recharge.setCzzt("2");
			update(recharge);
			return "SUCCESS";
		}else{
			recharge.setCzzt("3");
			update(recharge);
			return "WORDS NOT MATCH";
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
			return  ApiResponse.success(maps);
    }



    @Override
	public void afterPager(PageInfo<Recharge> info){
		List<Recharge> list = info.getList();
		if(CollectionUtils.isEmpty(list)){
			return;
		}

		Set<String> set = list.stream().map(Recharge::getUserId).collect(Collectors.toSet());
		List<User> users = userService.findByIds(set);
		Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
		list.forEach(recharge -> {
			if(userMap.containsKey(recharge.getUserId())){
				User user = userMap.get(recharge.getUserId());
				recharge.setUsername(user.getUserName());
				recharge.setHimg(user.gethImg());
			}
		});
	}

}