package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.LuckNumMapper;
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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.*;
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

	@Autowired
	private ProInfoService proInfoService;

	@Autowired
	private LuckNumMapper luckNumMapper;

	@Autowired
	private WinRecordService recordService;

	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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
		ProBaseinfo baseinfo;
		if (entity.getOrderType().equals("1")) {
			baseinfo = proBaseinfoService.findById(entity.getProId());

		}else{
			String baseId = proInfoService.getBaseId(entity.getProId());
			baseinfo = proBaseinfoService.findById(baseId);
		}

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

			save(order);
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

		order.setZfsj(DateUtils.getNowTime());

		// 已支付 生成消费记录

		exchange.setId(genId());
		exchange.setProid(order.getProId());
		exchange.setPromc(order.getProName());
		exchange.setUserid(userId);
		exchange.setXfddh(order.getId());

		exchangeService.save(exchange);
		userService.update(user);
		update(order);
		if(StringUtils.equals(order.getOrderType(), "2")){

			// 订单支付完成 分配号码
			// 先查询当前号码分配到多少位
			ProInfo proInfo = proInfoService.findById(order.getProId());
			Integer sort = proInfo.getSort();
			 // 根据购买份数 分配号码
			int anInt = Integer.parseInt(order.getGmfs());
			int nu = sort + anInt;
			List<String> luckNum = luckNumMapper.getLuckNum(sort, nu,proInfo.getProBaseid());
			List<OrderList> orderLists = new ArrayList<>();
			for (String s : luckNum) {
				OrderList orderList = new OrderList(order,s,user);
				orderLists.add(orderList);
			}
			if(CollectionUtils.isNotEmpty(orderLists)){
				orderListService.saveList(orderLists);
				proInfo.setSort(nu);
				proInfo.setGxsj(DateUtils.getNowTime());
				proInfoService.update(proInfo);
			}
			// 查看商品剩余名额是否 为 0
			String rePrice = proInfo.getRePrice();
			if(StringUtils.equals(rePrice, "0")){
				// 建立延时任务 , 准备分配中奖号码

				executorService.schedule(() -> fenpei(proInfo.getId()),5, TimeUnit.MINUTES);

			}


		}
		return ApiResponse.success(MessageUtils.get("order.paySuc"));
	}


	/**
	 * 中奖号码分配
	 * @param id 上架商品id
	 */
	private void fenpei(String id){
		ProInfo info = proInfoService.findById(id);
		// 首先获取 最后五十个商品的购买时间
		List<String> lastFifty = baseMapper.getLastFifty();
		// 所有时间 按 HHmmssSSS 相加
		Long hHmmssSSS = lastFifty.stream().map(s -> Long.parseLong(DateTime.parse(s).toString("HHmmssSSS"))).reduce(Long::sum).get();
		// 时间总数除以需求总数 取余
		long zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;

		info.setZjhm(zjhm + "");
		info.setProZt("3");
		info.setKjsj(DateUtils.getNowTime());
		info.setGxsj(DateUtils.getNowTime());

		// 查询中奖人 id
		SimpleCondition condition = new SimpleCondition(OrderList.class);
		condition.eq(OrderList.InnerColumn.num, zjhm+"");
		condition.eq(OrderList.InnerColumn.proId, info.getId());
		List<OrderList> lists = orderListService.findByCondition(condition);
		if(CollectionUtils.isNotEmpty(lists)){
			OrderList list = lists.get(0);
			info.setUserId(list.getUserid());
			info.setUserName(list.getUserName());
			// 将此单的状态改为已中奖
			Order order = findById(list.getOrderId());
			order.setDdzt("1");
			order.setZjhm(zjhm + "");
			update(order);
			// 将所有没有中奖的订单重置为未中奖
			baseMapper.updateDdztToLost(info.getId());
			// 用户中奖次数加 1
			User user = userService.findById(list.getUserid());
			user.setZjcs(Integer.parseInt(user.getZjcs()) + 1 + "");
			userService.update(user);
			// 中奖记录生成
			WinRecord record = new WinRecord();
			record.setId(genId());
			record.setCjsj(DateUtils.getNowTime());
			record.setNum(zjhm + "");
			record.setProId(info.getId());
			record.setUserId(user.getId());
			record.setUserName(user.getUserName());
			SimpleCondition simpleCondition = new SimpleCondition(Order.class);
			simpleCondition.eq(Order.InnerColumn.proId, info.getId());
			simpleCondition.eq(Order.InnerColumn.userId, user.getId());
			List<Order> orders = findByCondition(simpleCondition);
			int sum = orders.stream().map(order1 -> Integer.parseInt(order1.getGmfs())).mapToInt(value -> value).sum();
			record.setZjfs(sum+"");
			record.setZjlx(user.getScore());
			recordService.save(record);
		}

	}



}