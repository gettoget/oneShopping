package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.mapper.ProInfoMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.AndroidMsgBean;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.BaiduPushUtils;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.exception.RuntimeCheckException;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    Logger errorLog = LoggerFactory.getLogger("error_info");

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
    private ProInfoService proInfoService;
    @Autowired
    private ProInfoMapper infoMapper;

    @Autowired
    private ReceiveAddrService addrService;


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
    public boolean fillPagerCondition(LimitedCondition condition) {

        return true;
    }

    @Override
    public void afterPager(PageInfo<Order> result) {
        if (CollectionUtils.isEmpty(result.getList())) {
            return;
        }
        List<Order> orders = result.getList();
        List<String> list = orders.stream().map(Order::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(list)) {
            Set<String> receIds = orders.stream().filter(order -> StringUtils.isNotBlank(order.getReceId())).map(Order::getReceId).collect(Collectors.toSet());
            Map<String, ReceiveAddr> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(receIds)) {

                List<ReceiveAddr> addrs = addrService.findByIds(receIds);
                addrMap = addrs.stream().collect(Collectors.toMap(ReceiveAddr::getId, p -> p));
            }
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.in(OrderList.InnerColumn.orderId, list);
            List<OrderList> orderLists = orderListService.findByCondition(condition);

            if (CollectionUtils.isNotEmpty(orderLists)) {
                Map<String, List<OrderList>> listMap = orderLists.stream().collect(Collectors.groupingBy(OrderList::getOrderId));
                Set<String> collect = orderLists.stream().filter(orderList -> StringUtils.isNotBlank(orderList.getProId())).map(OrderList::getProId).collect(Collectors.toSet());
                Set<String> userIds = orderLists.stream().filter(orderList -> StringUtils.isNotBlank(orderList.getUserid())).map(OrderList::getUserid).collect(Collectors.toSet());

                List<User> users = userService.findByIds(userIds);
                Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));

                List<ProInfo> infos = proInfoService.findByIds(collect);
                Map<String, ProInfo> map = infos.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));
                Map<String, ReceiveAddr> finalAddrMap = addrMap;
                orders.forEach(order -> {
                    if (listMap.containsKey(order.getId())) {
                        List<OrderList> lists = listMap.get(order.getId());
                        order.setOrderLists(lists);
                        List<String> nums = lists.stream().map(OrderList::getNum).collect(Collectors.toList());
                        order.setNums(nums);
                    }
                    if (map.containsKey(order.getProId())) {
                        ProInfo info = map.get(order.getProId());
                        order.setRate((Integer.parseInt(info.getProPrice()) - Integer.parseInt(info.getRePrice()) * 100) / Integer.parseInt(info.getProPrice()));
                    }
                    if (userMap.containsKey(order.getUserId())) {
                        User user = userMap.get(order.getUserId());
                        order.setUserName(user.getUserName());
                    }
                    if (finalAddrMap.containsKey(order.getReceId())) {
                        ReceiveAddr addr = finalAddrMap.get(order.getReceId());
                        order.setAddr(addr);
                    }
                });
            }
        }
    }

    @Override
    public ApiResponse<String> saveEntity(Order entity) {
        String imei = getAttributeAsString("imei");
        Object o = redis.boundValueOps(imei + "saveOrder").get();
        if (o != null) {
            return ApiResponse.fail(MessageUtils.get("FrequentOperation"));
        } else {
            redis.boundValueOps(imei + "saveOrder").set(1, 10, TimeUnit.SECONDS);
        }
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user1 = userService.findById(userId);
        RuntimeCheck.ifNull(user1, MessageUtils.get("user.null"));
        RuntimeCheck.ifBlank(entity.getOrderType(), MessageUtils.get("order.typeBlank"));
        RuntimeCheck.ifFalse(entity.getOrderType().equals("1") || entity.getOrderType().equals("2"), MessageUtils.get("order.typeError"));
        RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("order.proBlank"));
        RuntimeCheck.ifBlank(entity.getZfje(), MessageUtils.get("order.jeBlank"));
        RuntimeCheck.ifBlank(entity.getGmfs(), MessageUtils.get("order.gmfsBlank"));
        RuntimeCheck.ifBlank(entity.getReceId(), MessageUtils.get("order.receIsBlank"));
        RuntimeCheck.ifBlank(entity.getPayPwd(), MessageUtils.get("user.paypwdblank"));

        int gmfs = Integer.parseInt(entity.getGmfs());
        RuntimeCheck.ifTrue(gmfs < 0, MessageUtils.get("order.gmLteZero"));
        int dzf = Integer.parseInt(entity.getZfje());
        RuntimeCheck.ifTrue(dzf < 0, MessageUtils.get("order.jeLteZero"));
        ProBaseinfo baseinfo = null;
        ProInfo proInfo = null;


        // 根据订单类型查询商品信息 1 为 直接购买  2 为参与抽奖
        if (entity.getOrderType().equals("1")) {
            baseinfo = proBaseinfoService.findById(entity.getProId());
            int store = Integer.parseInt(baseinfo.getProStore());
            RuntimeCheck.ifTrue(gmfs > store, MessageUtils.get("pro.stroeNotEnough"));
        } else if (StringUtils.equals(entity.getOrderType(), "2")) {
            proInfo = proInfoService.findById(entity.getProId());
            int reprice = Integer.parseInt(proInfo.getRePrice());
            RuntimeCheck.ifTrue(gmfs > reprice, MessageUtils.get("pro.stroeNotEnough"));
            String baseId = proInfo.getProBaseid();
            baseinfo = proBaseinfoService.findById(baseId);
        }
        RuntimeCheck.ifNull(baseinfo, MessageUtils.get("pro.isNull"));
        // 创建订单
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
        order.setReceId(entity.getReceId());
        order.setZfje(entity.getZfje());
        order.setImei(imei);
        // 生成消费记录
        Exchange exchange = new Exchange();

        exchange.setXfjb(dzf + "");
        if (StringUtils.equals(entity.getOrderType(), "1")) {
            /*// 直接购买
            order.setZfje(Integer.parseInt(baseinfo.getProPrice()) * Integer.parseInt(order.getGmfs()) + "");*/
            // 直接购买  baseInfo 库存 -1
            //            baseMapper.minusStore(baseinfo.getId(), Integer.parseInt(order.getGmfs()));
            String payType = getRequestParamterAsString("payType");
            // todo 直接购买可能是直接调用支付接口
            // 如果不是调用的支付接口 则使用余额购买
            if (StringUtils.isBlank(payType)) {
                int balance = Integer.parseInt(user1.getBalance());
                int ye = balance - dzf;
                RuntimeCheck.ifTrue(ye < 0, MessageUtils.get("order.balanceNotEnough"));
                exchange.setXfqjbs(balance + "");
                exchange.setXfsj(DateUtils.getNowTime());
                exchange.setXfhjbs(ye + "");
                user1.setBalance(ye + "");
            } else {
                // 调用支付接口
            }
            // 直接购买订单 订单状态改为已支付
            order.setDdzt("4");
        } else if (StringUtils.equals(entity.getOrderType(), "2")) {

            // 抽奖 支付后直接进入待开奖状态
            order.setDdzt("0");
            int balance = Integer.parseInt(user1.getBalance());
            int ye = balance - dzf;
            RuntimeCheck.ifTrue(ye < 0, MessageUtils.get("order.balanceNotEnough"));
            exchange.setXfqjbs(balance + "");
            exchange.setXfsj(DateUtils.getNowTime());
            exchange.setXfhjbs(ye + "");
            user1.setBalance(ye + "");
        } else {
            return ApiResponse.fail(MessageUtils.get("order.typeError"));
        }

        order.setZfsj(DateUtils.getNowTime());

        // 已支付 生成消费记录
        exchange.setId(genId());
        exchange.setProid(order.getProId());
        exchange.setPromc(order.getProName());
        exchange.setUserid(userId);
        exchange.setXfddh(order.getId());

        if (StringUtils.equals(order.getOrderType(), "2")) {
            // 参与抽奖 剩余名额需要减去购买份数
            // 订单支付完成 分配号码
            // 根据购买份数 分配号码
            int anInt = Integer.parseInt(order.getGmfs());
            // 判断库存是否足够
            BoundSetOperations<Object, Object> luckNumSet = redis.boundSetOps(order.getProId() + "_nums");
            RuntimeCheck.ifTrue(luckNumSet.size() < anInt, MessageUtils.get("pro.stroeNotEnough"));
            // 获取当前商品 剩余的中奖号码
            Set<Object> objects = luckNumSet.members();
            List<Object> elements = new ArrayList<>();
            Iterator<Object> ites = objects.iterator();
            int index = 0;
            while (ites.hasNext()) {
                Object element = ites.next();
                // 防止并发操作时，中奖号码被重复分。先进行删除，redis删除结果不为0，表示号码可用
                long removeFlag = luckNumSet.remove(element);
                if (removeFlag > 0) {
                    elements.add(element);
                    // 中奖号码未被使用，标记提取成功
                    index++;
                }
                // 用户购买号码数量提取成功后结束号码提取过程
                if (index == anInt) {
                    break;
                }
            }
            try {
                List<OrderList> orderLists = new ArrayList<>();
                for (int i = 0; i < elements.size(); i++) {
                    ProInfoLuckNumBean num = (ProInfoLuckNumBean) elements.get(i);
                    OrderList orderList = new OrderList(order, num.getLuckNum(), user1, DateTime.now().plus(i).toString("yyyy-MM-dd HH:mm:ss.SSS"));
                    orderList.setId(genId());
                    orderLists.add(orderList);
                }
                // 查看商品剩余名额是否 为 0   且所有订单都已支付
            /*proInfo.setGxsj(DateUtils.getNowTime());
            proInfo.setCyyhs(Integer.parseInt(proInfo.getCyyhs()) + 1 + "");*/
                // 获取中奖号
                if (CollectionUtils.isNotEmpty(orderLists)) {
                    orderListService.saveList(orderLists);
                }
                int updateNum = baseMapper.minusRePrice(gmfs, order.getProId());
                if (updateNum == 0) {
                    throw new RuntimeCheckException(MessageUtils.get("pro.minusFail"));
                }
                if (luckNumSet.size() == 0) {
                    // 号码分配完 清理redis key
                    redis.delete(order.getProId() + "_nums");
                    infoMapper.updateFinish(order.getProId());
                    // 建立延时任务 , 准备分配中奖号码
                    long millis = DateTime.now().plusMinutes(1).getMillis();
                    redis.boundZSetOps(ProInfo.class.getSimpleName() + "_award").add(proInfo.getId(), millis);
                    ProInfo info = proInfoService.findById(proInfo.getId());
                    AndroidMsgBean msgBean = new AndroidMsgBean();
                    msgBean.setType("3");
                    msgBean.setJson(JsonUtil.toJson(info));
                    BaiduPushUtils.pushAllMsg(0, JsonUtil.toJson(msgBean), 3, 0);
                } else {
                    infoMapper.updateProInfo(order.getProId());
                }
            } catch (Exception e) {
                // 异常处理 , 将号码添加回去
                if (elements.size() > 0) {
                    // 执行发生异常后，需要先将中奖号码回压到redis中，防止号码丢失
                    for (int i = 0; i < elements.size(); i++) {
                        redis.boundSetOps(order.getProId() + "_nums").add(elements.get(i));
                    }
                }
                // 继续抛出异常，保证数据库事务回滚
                throw e;
            }
        } else if (StringUtils.equals(order.getOrderType(), "1")) {
            baseMapper.minusStore(baseinfo.getId(), Integer.parseInt(order.getGmfs()));
        }
        exchangeService.save(exchange);
        userService.update(user1);
        save(order);

        return ApiResponse.success(MessageUtils.get("order.paySuc"));

    }


    @Override
    public PageResponse<Order> getPageInfo(Page<Order> page) {
        PageResponse<Order> res = new PageResponse<>();

        String userId = getAttributeAsString("userId");
        if (StringUtils.isNotBlank(userId)) {
            LimitedCondition condition = getQueryCondition();
            condition.eq(Order.InnerColumn.userId, userId);
            // 查询当前用户的订单
            PageInfo<Order> pageInfo = findPage(page, condition);

            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                // 拿到所有的 商品 id
                List<String> ids = pageInfo.getList().stream().map(Order::getProId).collect(Collectors.toList());
                List<ProInfo> info = proInfoService.findByIds(ids);
                // 根据商品 id 生成 map
                Map<String, ProInfo> map = info.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));

                Map<String, String> userMap = new HashMap<>();
                // 拿到每个商品的 中奖人的 id
                List<String> userIds = info.stream().filter(proInfo -> StringUtils.isNotBlank(proInfo.getUserId())).map(ProInfo::getUserId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(userIds)) {
                    List<User> users = userService.findByIds(userIds);
                    // 根据中奖人 id 分组
                    userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p.getUserName()));
                }
                // 拿到所有 收货地址 的 id
                List<String> collect = pageInfo.getList().stream().map(Order::getReceId).collect(Collectors.toList());
                List<ReceiveAddr> addrs = addrService.findByIds(collect);
                // 收货地址分组
                Map<String, ReceiveAddr> listMap = addrs.stream().collect(Collectors.toMap(ReceiveAddr::getId, p -> p));

                Map<String, String> finalUserMap = userMap;
                pageInfo.getList().stream().forEach(order -> {
                    if (map.containsKey(order.getProId())) {
                        ProInfo proInfo = map.get(order.getProId());
                        order.setCoverUrl(proInfo.getCoverUrl());
                        order.setKjsj(proInfo.getKjsj());
                        order.setRate((Integer.parseInt(proInfo.getProPrice()) - Integer.parseInt(proInfo.getRePrice())) * 100 / Integer.parseInt(proInfo.getProPrice()));
                        if (finalUserMap.containsKey(proInfo.getUserId())) {
                            order.setUserName(finalUserMap.get(proInfo.getUserId()));
                        }
                    }
                    order.setAddr(listMap.get(order.getReceId()));
                });
            }
            res.setTotal(pageInfo.getTotal());
            res.setPageSize(page.getPageSize());
            res.setList(pageInfo.getList());
            res.setPageNum(pageInfo.getPageNum());
        }

        return res;
    }

    @Override
    public ApiResponse<String> updateOrderCancel(String id) {
        Order order = findById(id);
        RuntimeCheck.ifFalse(order.getDdzt().equals("3"), MessageUtils.get("order.ztError"));
        order.setDdzt("5");
        update(order);
        // 查看订单状态为直接购买还是参与抽奖
        // 直接购买订单 将库存返回
        if (StringUtils.equals(order.getOrderType(), "1")) {
            baseMapper.plusStore(order.getProId(), Integer.parseInt(order.getGmfs()));
        } else {
            // 抽奖订单将 剩余名额返回
            baseMapper.plusRePrice(order.getProId(), Integer.parseInt(order.getGmfs()));
        }
        return ApiResponse.success();
    }


    /**
     * 中奖号码分配
     *
     * @param id 上架商品id
     */
    @Override
    public void fenpei(String id) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        //查询状态为代开将的商品
        ProInfo entity = new ProInfo();
        entity.setId(id);
        entity.setProZt("3");
        ProInfo info = proInfoService.findOneByEntity(entity);
        if (info == null) {
            return;
        }

        List<OrderList> lastFifty = baseMapper.getLastFifty(info.getId(), 50);

        long zjhm = 0l;
        // 如果是需要机器人中奖，从机器人中抽取一个中间号码
        if (StringUtils.equals(info.getrType(), "2")) {
            //获取最后一个机器人参与的订单号码
            OrderList lastOrder = baseMapper.findLatestRobot(info.getId());
            //从最后一个购买订单的抽取一个中间号码
            OrderList orderList = baseMapper.getOrderByRobotZjhm(id);

            if (orderList != null && StringUtils.isNotBlank(lastOrder.getId())) {
                List<String> strings = lastFifty.stream().map(OrderList::getId).collect(Collectors.toList());
                String hm = orderList.getNum();
                //如果最后50个订单里面没有中间号码订单，则将中奖号码订单添加进去
                if (!strings.contains(lastOrder.getId())) {
                    lastFifty = lastFifty.subList(0, lastFifty.size() - 1);
                    lastOrder.setCjsj(lastFifty.get(lastFifty.size() - 1).getCjsj());
                    lastFifty.add(lastOrder);
                }
                Long hHmmssSSS = lastFifty.stream().map(OrderList::getCjsj).map(s -> Long.parseLong(DateTime.parse(s, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
                zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;
                int anInt = Integer.parseInt(hm);
                int l = (int) (anInt - zjhm);
                if (l < 0) {
                    l += Long.parseLong(info.getProPrice());
                }
                String s;
                if (!strings.contains(lastOrder.getId())) {
                    s = DateTime.parse(lastFifty.get(lastFifty.size() - 1).getCjsj(), formatter).plusMillis(l).toString("yyyy-MM-dd HH:mm:ss.SSS");
                } else {
                    s = DateTime.parse(lastOrder.getCjsj(), formatter).plusMillis(l).toString("yyyy-MM-dd HH:mm:ss.SSS");
                }
                lastOrder.setCjsj(s);
                /*lastOrder.setZfsj(s);
                lastOrder.setCjsj(s);
                update(lastOrder);*/
                orderListService.update(lastOrder);
               /* lastFifty = baseMapper.getLastFifty(info.getId(), 50);
                hHmmssSSS = lastFifty.stream().map(OrderList::getCjsj).map(s1 -> Long.parseLong(DateTime.parse(s1, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
                zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;*/
                zjhm = Long.parseLong(hm);
            }
        } else {
            lastFifty = baseMapper.getLastFifty(info.getId(), 50);
            // 所有时间 按 HHmmssSSS 相加
            Long hHmmssSSS = lastFifty.stream().map(OrderList::getCjsj).map(s -> Long.parseLong(DateTime.parse(s, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
            zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;
        }
        // 时间总数除以需求总数 取余

        info.setZjhm(zjhm + "");
        info.setProZt("4");
        info.setGxsj(DateUtils.getNowTime());

        // 查询中奖人 id
        SimpleCondition condition = new SimpleCondition(OrderList.class);
        condition.eq(OrderList.InnerColumn.num, zjhm + "");
        condition.eq(OrderList.InnerColumn.proId, info.getId());
        List<OrderList> lists = orderListService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(lists)) {
            OrderList list = lists.get(0);
            info.setUserId(list.getUserid());
            info.setUserName(list.getUserName());
            proInfoService.update(info);
            // 将此单的状态改为已中奖
            Order order = findById(list.getOrderId());
            order.setDdzt("1");
            order.setZjhm(zjhm + "");
            update(order);
            // 将所有没有中奖的订单重置为未中奖
            baseMapper.updateDdztToLost(info.getId(), zjhm + "");
            // 用户中奖次数加 1-------这里不需要先查询一次再update一次，直接使用update语句同步更新即可，也可以防止事务和并发问题
            User user = userService.findById(list.getUserid());
            baseMapper.updateZjcs(user.getId());
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
            //int sum = orders.stream().map(order1 -> Integer.parseInt(order1.getGmfs())).mapToInt(value -> value).sum();
            record.setZjfs(order.getGmfs());
            record.setZjlx(user.getScore());
            recordService.save(record);

            info.setUserName(user.getUserName());
            info.setZjfs(record.getZjfs());
            AndroidMsgBean msgBean = new AndroidMsgBean();
            msgBean.setType("4");
            msgBean.setJson(JsonUtil.toJson(info));
            BaiduPushUtils.pushAllMsg(0, JsonUtil.toJson(msgBean), 3, 0);
            // 商品已中奖  自动上架 下个商品  如果还有库存的话
            ProBaseinfo baseinfo = proBaseinfoService.findById(info.getProBaseid());
            if (Integer.parseInt(baseinfo.getProStore()) > 0) {
                redis.convertAndSend("grounding", baseinfo.getId());
            }
        }

    }

    @Override
    public void saveList(List<Order> orders) {
        baseMapper.insertList(orders);
    }


}