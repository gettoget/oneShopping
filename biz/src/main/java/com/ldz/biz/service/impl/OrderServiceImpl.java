package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

	@Autowired
	private OrderMapper baseMapper;

	@Autowired
	private OrderListService orderListService;

	@Autowired
	private ProBaseinfoService proBaseinfoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private ProEvalService proEvalService;
	
	@Override
	protected Mapper<Order> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		String userId = (String) getAttribute("userId");
		if(StringUtils.isBlank(userId)){
			return false;
		}
		condition.eq(Order.InnerColumn.userId ,userId);
		return true;
	}

	@Override
	public void afterPager(PageInfo<Order> result){
		if (CollectionUtils.isEmpty(result.getList())) {
			return;
		}
		List<Order> orders = result.getList();
		List<String> list = orders.stream().filter(order -> StringUtils.equals(order.getOrderType(), "2")).map(Order::getId).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(list)){
			String userId = (String) getAttribute("userId");

			SimpleCondition condition = new SimpleCondition(OrderList.class);
			condition.eq(OrderList.InnerColumn.userid, userId);
			condition.in(OrderList.InnerColumn.orderId, list);
			List<OrderList> orderLists = orderListService.findByCondition(condition);

			if(CollectionUtils.isNotEmpty(orderLists)){
				Map<String, List<OrderList>> listMap = orderLists.stream().collect(Collectors.groupingBy(OrderList::getOrderId));
				orders.forEach(order -> {
					if(listMap.containsKey(order.getId())){
						order.setOrderLists(listMap.get(order.getId()));
					}
				});
			}
		}
	}

	@Override
	public ApiResponse<String> saveEntity(Order entity) {
		RuntimeCheck.ifBlank(entity.getOrderType(), MessageUtils.get("order.typeBlank"));
		RuntimeCheck.ifFalse(Arrays.asList("1", "2").contains(entity.getOrderType()), MessageUtils.get("order.typeError"));
		RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("order.proBlank"));
		RuntimeCheck.ifBlank(entity.getZfje(), MessageUtils.get("order.jeBlank"));
		String userId = (String) getAttribute("userId");
		String imei = (String) getAttribute("imei");
		ProBaseinfo baseinfo = proBaseinfoService.findById(entity.getProId());
		RuntimeCheck.ifNull(baseinfo, MessageUtils.get("pro.isNull"));
		Order order = new Order();
		order.setId(genId());
		order.setCjsj(DateUtils.getNowTime());
		order.setImei(imei);
		order.setDdzt("3");
		order.setOrderType(entity.getOrderType());
		order.setProId(entity.getProId());
		order.setProName(baseinfo.getProName());
		order.setUserId(userId);
		if(StringUtils.equals(entity.getOrderType(), "1")){
			// 直接购买
			order.setZfje(baseinfo.getProPrice());
			save(order);
			return ApiResponse.success(MessageUtils.get("order.saveSuc"));
		}else if(StringUtils.equals(entity.getOrderType(), "2")){
			// 参与抽奖
			RuntimeCheck.ifBlank(entity.getGmfs(), MessageUtils.get("order.gmfsBlank"));

			order.setZfje(entity.getGmfs());
			order.setGmfs(entity.getGmfs());
			// 抽奖号码生成  根据购买份数生成
			List<OrderList> orderLists = new ArrayList<>();
			for(int i = 0 ; i < Integer.parseInt(entity.getGmfs()); i++){
				OrderList o = new OrderList();
				o.setId(genId());
				o.setCjsj(order.getCjsj());
				// todo 中奖号码生成方法
				o.setNum("1" + i);
				o.setOrderId(order.getId());
				o.setProId(order.getProId());
				o.setProName(order.getProName());
				o.setUserid(userId);
				User user = userService.findById(userId);
				o.setUserName(user.getUserName());
				o.setYhlx(user.getSource());
				orderLists.add(o);
			}
			save(order);
			orderListService.saveList(orderLists);
			return ApiResponse.success(MessageUtils.get("order.saveSuc"));
		}else{
			return ApiResponse.fail(MessageUtils.get("order.typeError"));
		}

	}

	@Override
	public ApiResponse<String> payOrder(String id, String payPwd) {
		RuntimeCheck.ifBlank(id, MessageUtils.get("order.idBlank"));
		String userId = (String) getAttribute("userId");
		User user = userService.findById(userId);
		RuntimeCheck.ifBlank(user.getPayPwd(), MessageUtils.get("user.paypwdnull"));
		RuntimeCheck.ifBlank(payPwd, MessageUtils.get("user.paypwdblank"));
		Order order = findById(id);
		RuntimeCheck.ifFalse(order.getDdzt().equals("3"), MessageUtils.get("order.ztError"));
		Exchange exchange = new Exchange();
		int dzf = Integer.parseInt(order.getZfje());
		exchange.setXfjb(dzf+"");
		if (StringUtils.equals(order.getOrderType(), "1")) {
			// todo 直接购买可能是直接调用支付接口
			// 直接购买订单 订单状态改为已支付
			order.setDdzt("4");

		}else{
			// 抽奖 支付后直接进入待开奖状态
			order.setDdzt("0");
			int balance = Integer.parseInt(user.getBalance());
			int ye = balance - dzf;
			if(ye < 0){
				return ApiResponse.fail(MessageUtils.get("order.balanceNotEnough"));
			}
			exchange.setXfqjbs(balance+"");
			exchange.setXfsj(DateUtils.getNowTime());
			exchange.setXfhjbs(ye+"");
			user.setBalance(ye + "");
		}



		// 已支付 生成消费记录

		exchange.setId(genId());
		exchange.setProid(order.getProId());
		exchange.setPromc(order.getProName());
		exchange.setUserid(userId);
		exchange.setXfddh(order.getId());



		exchangeService.save(exchange);
		userService.update(user);
		update(order);
		return ApiResponse.success(MessageUtils.get("order.paySuc"));
	}
}