package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.EncryptUtil;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.exception.RuntimeCheckException;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
        String userId = (String) getAttribute("userId");
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        condition.eq(Order.InnerColumn.userId, userId);
        return true;
    }

    @Override
    public void afterPager(PageInfo<Order> result) {
        if (CollectionUtils.isEmpty(result.getList())) {
            return;
        }
        List<Order> orders = result.getList();
        List<String> list = orders.stream().filter(order -> StringUtils.equals(order.getOrderType(), "2")).map(Order::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(list)) {
            String userId = (String) getAttribute("userId");

            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.userid, userId);
            condition.in(OrderList.InnerColumn.orderId, list);
            List<OrderList> orderLists = orderListService.findByCondition(condition);

            if (CollectionUtils.isNotEmpty(orderLists)) {
                Map<String, List<OrderList>> listMap = orderLists.stream().collect(Collectors.groupingBy(OrderList::getOrderId));
                orders.forEach(order -> {
                    if (listMap.containsKey(order.getId())) {
                        order.setOrderLists(listMap.get(order.getId()));
                    }
                });
            }
        }
    }

    @Override
    public ApiResponse<String> saveEntity(Order entity) {
        String imei = getAttributeAsString("imei");
        String userId = getAttributeAsString("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        User user1 = userService.findById(userId);
        RuntimeCheck.ifNull(user1, MessageUtils.get("user.null"));
        Object o = redis.boundValueOps(imei + "saveOrder").get();
        if (o != null) {
            return ApiResponse.fail(MessageUtils.get("FrequentOperation"));
        } else {
            redis.boundValueOps(imei + "saveOrder").set(1, 10, TimeUnit.SECONDS);
        }
        RuntimeCheck.ifBlank(entity.getOrderType(), MessageUtils.get("order.typeBlank"));
        RuntimeCheck.ifFalse(entity.getOrderType().equals("1") || entity.getOrderType().equals("2"), MessageUtils.get("order.typeError"));
        RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("order.proBlank"));
        RuntimeCheck.ifBlank(entity.getZfje(), MessageUtils.get("order.jeBlank"));
        RuntimeCheck.ifBlank(entity.getGmfs(), MessageUtils.get("order.gmfsBlank"));
        RuntimeCheck.ifBlank(entity.getReceId(), MessageUtils.get("order.receIsBlank"));
        RuntimeCheck.ifBlank(entity.getPayPwd(), MessageUtils.get("user.paypwdblank"));


        ProBaseinfo baseinfo = null;
        ProInfo proInfo = null;
        int gmfs = Integer.parseInt(entity.getGmfs());
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
        // 生成消费记录
        Exchange exchange = new Exchange();
        int dzf = Integer.parseInt(entity.getZfje());
        exchange.setXfjb(dzf + "");
        if (StringUtils.equals(entity.getOrderType(), "1")) {
            /*// 直接购买
            order.setZfje(Integer.parseInt(baseinfo.getProPrice()) * Integer.parseInt(order.getGmfs()) + "");*/
            // 直接购买  baseInfo 库存 -1
            //            baseMapper.minusStore(baseinfo.getId(), Integer.parseInt(order.getGmfs()));
            // todo 直接购买可能是直接调用支付接口
            // 直接购买订单 订单状态改为已支付
            order.setDdzt("4");
        } else if (StringUtils.equals(entity.getOrderType(), "2")) {

            // 抽奖 支付后直接进入待开奖状态
            order.setDdzt("0");
            int balance = Integer.parseInt(user1.getBalance());
            int ye = balance - dzf;
            if (ye < 0) {
                return ApiResponse.fail(MessageUtils.get("order.balanceNotEnough"));
            }
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

        exchangeService.save(exchange);
        userService.update(user1);

        save(order);

        if (StringUtils.equals(order.getOrderType(), "2")) {
            // 参与抽奖 剩余名额需要减去购买份数

            // 查看当前用户是否已经参与过
            SimpleCondition oderCondition = new SimpleCondition(Order.class);
            oderCondition.eq(Order.InnerColumn.userId, userId);
            oderCondition.eq(Order.InnerColumn.proId, proInfo.getId());
            oderCondition.notIn(Order.InnerColumn.ddzt, Arrays.asList("3", "5"));
            oderCondition.and().andCondition(" id !=  '" + order.getId() + "'");
            List<Order> orderList1 = findByCondition(oderCondition);
            if (CollectionUtils.isEmpty(orderList1)) {
                proInfo.setCyyhs(Integer.parseInt(proInfo.getCyyhs()) + 1 + "");
            }
            // 订单支付完成 分配号码
            // 根据购买份数 分配号码
            int anInt = Integer.parseInt(order.getGmfs());
            // 获取当前商品 剩余的中奖号码
            Set<Object> objects = redis.boundSetOps(order.getProId() + "_nums").distinctRandomMembers(anInt);
            List<Object> elements = new ArrayList<>();
            CollectionUtils.addAll(elements, objects);
            List<OrderList> orderLists = new ArrayList<>();
            RuntimeCheck.ifTrue(elements.size() < anInt, MessageUtils.get("pro.stroeNotEnough"));
            for (int i = 0; i < elements.size(); i++) {
                ProInfoLuckNumBean num = (ProInfoLuckNumBean) elements.get(i);
                OrderList orderList = new OrderList(order, num.getLuckNum(), user1);
                orderList.setId(genId());
                orderLists.add(orderList);
            }
            // 获取中奖号
            if (CollectionUtils.isNotEmpty(orderLists)) {
                orderListService.saveList(orderLists);
            }
            // 查看商品剩余名额是否 为 0   且所有订单都已支付

            int rePrice = Integer.parseInt(proInfo.getRePrice()) - Integer.parseInt(order.getGmfs());
            proInfo.setGxsj(DateUtils.getNowTime());

            if (rePrice <= 0) {
                SimpleCondition condition = new SimpleCondition(Order.class);
                condition.eq(Order.InnerColumn.ddzt, "3");
                condition.eq(Order.InnerColumn.proId, proInfo.getId());
                List<Order> orders = findByCondition(condition);
                if (CollectionUtils.isEmpty(orders)) {
                    // 号码分配完 清理redis key
                    redis.delete(order.getProId() + "_nums");
                    // 更新商品状态为 待开奖

                    proInfo.setProZt("3");
                    proInfo.setKjsj(DateTime.now().plusMinutes(1).toString("yyyy-MM-dd HH:mm:ss.SSS"));

                    // 建立延时任务 , 准备分配中奖号码
                    ProInfo finalProInfo = proInfo;
                    executorService.schedule(() -> fenpei(finalProInfo.getId()), 1, TimeUnit.MINUTES);
                }

            }
            proInfoService.update(proInfo);
            int updateNum = baseMapper.minusRePrice(gmfs, order.getProId());
            if (updateNum == 0) {
                throw new RuntimeCheckException(MessageUtils.get("pro.minusFail"));
            }
            Iterator<Object> removeNum = objects.iterator();
            while (removeNum.hasNext()) {
                redis.boundSetOps(order.getProId() + "_nums").remove(removeNum.next());
            }
        }

        // 当剩余名额剩余过半时
        String s = (String) redis.boundValueOps(order.getProId() + "_robot").get();
        if (StringUtils.isBlank(s) && "2".equals(proInfo.getrType()) && Integer.parseInt(proInfo.getProPrice()) / Integer.parseInt(proInfo.getRePrice()) > 1 && Integer.parseInt(proInfo.getProPrice()) >= 2) {
            Set<Object> set = redis.boundSetOps(proInfo.getId() + "_nums").distinctRandomMembers(2);
            List<Object> objectList = new ArrayList<>();
            CollectionUtils.addAll(objectList, set);
            // 随机生成 2 个用户创建订单 , 负责控制号码
            List<User> users = baseMapper.ranUsers(set.size());
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                Order ord = new Order();
                ord.setId(genId());
                ord.setUserName(user.getUserName());
                ord.setUserId(user.getId());
                ord.setProName(proInfo.getProName());
                ord.setOrderType("2");
                ord.setProId(proInfo.getId());
                ord.setDdzt("0");
                ord.setGmfs("1");
                ord.setZfje("1");
                ord.setZfsj(DateUtils.getNowTime());
                ord.setImei(user.getRegImei());
                ord.setCjsj(DateUtils.getNowTime());
                save(ord);
                baseMapper.minusRePrice(1, proInfo.getId());
                baseMapper.updateCyyhs(proInfo.getId());
                // 分配号码
                ProInfoLuckNumBean num = (ProInfoLuckNumBean) objectList.get(i);
                OrderList orderList = new OrderList();
                orderList.setYhlx("1");
                orderList.setUserName(user.getUserName());
                orderList.setUserid(user.getId());
                orderList.setOrderId(ord.getId());
                orderList.setProId(proInfo.getId());
                orderList.setProName(proInfo.getProName());
                orderList.setNum(num.getLuckNum());
                orderList.setId(genId());
                orderList.setCjsj(DateUtils.getNowTime());
                orderListService.save(orderList);
                redis.boundValueOps(order.getProId() + "_robot").set(ord.getId());
            }
            Iterator<Object> removeNum = set.iterator();
            while (removeNum.hasNext()) {
                redis.boundSetOps(order.getProId() + "_nums").remove(removeNum.next());
            }
        }
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
                        order.setCoverUrl(map.get(order.getProId()).getCoverUrl());
                        order.setKjsj(map.get(order.getProId()).getKjsj());
                        if (finalUserMap.containsKey(map.get(order.getProId()).getUserId())) {
                            order.setUserName(finalUserMap.get(map.get(order.getProId()).getUserId()));
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
        ProInfo info = proInfoService.findById(id);
        List<Order> lastFifty = baseMapper.getLastFifty(info.getId(), 50);

        long zjhm;
        if (StringUtils.equals(info.getrType(), "2")) {
            // 从机器人的号码中随机取一个
            String ordId = (String) redis.boundValueOps(id + "_robot").get();
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.yhlx, "1");
            condition.eq(OrderList.InnerColumn.proId, info.getId());
            if (StringUtils.isBlank(ordId)) {
                ordId = baseMapper.findLatestRobot();
            }
            List<OrderList> lists = orderListService.findByCondition(condition);

            if (lists.size() >= 1 && StringUtils.isNotBlank(ordId)) {
                List<String> strings = lastFifty.stream().map(Order::getId).collect(Collectors.toList());
                int max = Math.max(0, lists.size());
                int num = RandomUtils.nextInt(max);
                String hm = lists.get(num).getNum();
                String orderId = ordId;
                Order order = findById(orderId);
                if (!strings.contains(orderId)) {
                    lastFifty = lastFifty.subList(0, lastFifty.size() - 1);
                    order.setZfsj(lastFifty.get(lastFifty.size() - 1).getZfsj());
                    lastFifty.add(order);
                }

                Long hHmmssSSS = lastFifty.stream().map(Order::getZfsj).map(s -> Long.parseLong(DateTime.parse(s, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
                zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;
                int anInt = Integer.parseInt(hm);
                int l = (int) (anInt - zjhm);
                if (l < 0) {
                    l += Long.parseLong(info.getProPrice());
                }
                String s;
                if (!strings.contains(orderId)) {
                    s = DateTime.parse(lastFifty.get(lastFifty.size() - 1).getZfsj(), formatter).plusMillis(l).toString("yyyy-MM-dd HH:mm:ss.SSS");
                } else {
                    s = DateTime.parse(order.getZfsj(), formatter).plusMillis(l).toString("yyyy-MM-dd HH:mm:ss.SSS");
                }
                order.setZfsj(s);
                order.setCjsj(s);
                update(order);
            }
        }
        lastFifty = baseMapper.getLastFifty(info.getId(), 50);
        // 所有时间 按 HHmmssSSS 相加
        Long hHmmssSSS = lastFifty.stream().map(Order::getZfsj).map(s -> Long.parseLong(DateTime.parse(s, formatter).toString("HHmmssSSS"))).reduce(Long::sum).get();
        zjhm = (hHmmssSSS % Long.parseLong(info.getProPrice())) + 10000001;


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

            //int sum = orders.stream().map(order1 -> Integer.parseInt(order1.getGmfs())).mapToInt(value -> value).sum();
            record.setZjfs(order.getGmfs());
            record.setZjlx(user.getScore());
            recordService.save(record);
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