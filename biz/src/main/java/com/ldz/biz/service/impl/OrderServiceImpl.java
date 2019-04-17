package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
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
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
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
	private WinRecordService recordService;

	@Autowired
	private RedisTemplateUtil redis;

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
			String imei = (String) getAttribute("imei");
		String userId = (String) getAttribute("userId");
		RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
		Object o = redis.boundValueOps(imei + "saveOrder").get();
		if(o != null){
			return ApiResponse.fail(MessageUtils.get("FrequentOperation"));
		}else{
			redis.boundValueOps(imei + "saveOrder").set(1,10,TimeUnit.SECONDS);
		}
		RuntimeCheck.ifBlank(entity.getOrderType(), MessageUtils.get("order.typeBlank"));
		RuntimeCheck.ifFalse(entity.getOrderType().equals("1") ||entity.getOrderType().equals("2"), MessageUtils.get("order.typeError"));
		RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("order.proBlank"));
		RuntimeCheck.ifBlank(entity.getZfje(), MessageUtils.get("order.jeBlank"));
		RuntimeCheck.ifBlank(entity.getGmfs(), MessageUtils.get("order.gmfsBlank"));


		ProBaseinfo baseinfo;
		ProInfo proInfo = null;
		int gmfs = Integer.parseInt(entity.getGmfs());
		if (entity.getOrderType().equals("1")) {
			baseinfo = proBaseinfoService.findById(entity.getProId());
			int store = Integer.parseInt(baseinfo.getProStore());
			RuntimeCheck.ifTrue(gmfs > store, MessageUtils.get("pro.stroeNotEnough"));
		}else{
			proInfo = proInfoService.findById(entity.getProId());
			int reprice = Integer.parseInt(proInfo.getRePrice());
			RuntimeCheck.ifTrue(gmfs > reprice, MessageUtils.get("pro.stroeNotEnough"));
			String baseId = proInfo.getProBaseid();
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
		order.setGmfs(entity.getGmfs());
		if(StringUtils.equals(entity.getOrderType(), "1")){
			// 直接购买
			order.setZfje(Integer.parseInt(baseinfo.getProPrice()) * Integer.parseInt(order.getGmfs()) + "");
			save(order);
			// 直接购买  baseInfo 库存 -1
			baseMapper.minusStore(baseinfo.getId(),Integer.parseInt(order.getGmfs()));

		}else if(StringUtils.equals(entity.getOrderType(), "2")){
			// 参与抽奖
			order.setZfje(entity.getGmfs());
			save(order);
			// 参与抽奖 剩余名额需要减去购买份数
			baseMapper.minusRePrice(Integer.parseInt(order.getGmfs()),order.getProId());
		}else{
			return ApiResponse.fail(MessageUtils.get("order.typeError"));
		}
		// 存储订单支付 30 分钟过期 , 若未支付则  取消支付
		redis.boundValueOps(order.getId() + "_dzf").set(1,30,TimeUnit.MINUTES);
		return ApiResponse.success(MessageUtils.get("order.saveSuc"));
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
			 // 根据购买份数 分配号码
			int anInt = Integer.parseInt(order.getGmfs());
			List<String> luckNum = new ArrayList<>();
			// 获取当前商品 剩余的中奖号码
			for (int i = 0; i < anInt; i++) {
				String num = (String) redis.boundListOps(order.getProId() + "_nums").rightPop();
				luckNum.add(num);
			}

			List<OrderList> orderLists = new ArrayList<>();
			for (String s : luckNum) {
				OrderList orderList = new OrderList(order,s,user);
				orderLists.add(orderList);
			}
			if(CollectionUtils.isNotEmpty(orderLists)){
				orderListService.saveList(orderLists);
			}
			// 查看商品剩余名额是否 为 0   且所有订单都已支付
			ProInfo proInfo = proInfoService.findById(order.getProId());
			List<Order> orders = findEq(Order.InnerColumn.ddzt, "3");
			String rePrice = proInfo.getRePrice();
			if(StringUtils.equals(rePrice, "0") && CollectionUtils.isEmpty(orders)){
				// 建立延时任务 , 准备分配中奖号码
				executorService.schedule(() -> fenpei(proInfo.getId()),5, TimeUnit.MINUTES);
			}


		}
		return ApiResponse.success(MessageUtils.get("order.paySuc"));
	}

    @Override
    public ApiResponse<String> getPageInfo(Page<Order> page) {
		LimitedCondition condition = getQueryCondition();
		PageInfo<Order> pageInfo = findPage(page, condition);
		ApiResponse<String> res = new ApiResponse<>();
		res.setPage(pageInfo);
		return res;
    }

	@Override
	public ApiResponse<String> orderCancel(String id) {
		Order order = findById(id);
		RuntimeCheck.ifFalse(order.getDdzt().equals("3"), MessageUtils.get("order.ztError"));
		order.setDdzt("5");
		update(order);
		// 查看订单状态为直接购买还是参与抽奖
		// 直接购买订单 将库存返回
		if(StringUtils.equals(order.getOrderType(), "1")){
			baseMapper.plusStore(order.getProId(),Integer.parseInt(order.getGmfs()));
		}else{
			// 抽奖订单将 剩余名额返回
			baseMapper.plusRePrice(order.getProId(), Integer.parseInt(order.getGmfs()));
		}
		return ApiResponse.success();
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