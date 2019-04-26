package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Service;

import sun.awt.ModalityListener;
import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.mapper.ProInfoMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProInfoServiceImpl extends BaseServiceImpl<ProInfo, String> implements ProInfoService {

    @Autowired
    private ProInfoMapper baseMapper;

    @Autowired
    private ProBaseinfoService proBaseinfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private WinRecordService winRecordService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProInfoService proInfoService;



    @Value("${filePath}")
    private String filePath;


    @Autowired
    private RedisTemplateUtil redis;


    @Override
    protected Mapper<ProInfo> getBaseMapper() {
        return baseMapper;
    }


    @Override
    public ApiResponse<String> saveOne(String id) {
        ProBaseinfo baseinfo = proBaseinfoService.findById(id);
        RuntimeCheck.ifNull(baseinfo, MessageUtils.get("pro.isNull"));
        int storeNum = Integer.parseInt(baseinfo.getProStore());
        if (storeNum <= 0) {
            return ApiResponse.fail(MessageUtils.get("pro.storeIsNull"));
        }

        ProInfo proInfo = new ProInfo(baseinfo);
        proInfo.setId(genId());
        proInfo.setCjsj(DateUtils.getNowTime());
        proInfo.setCyyhs("0");
        proInfo.setGxsj(proInfo.getCjsj());
        proInfo.setProStore("1");
        proInfo.setProZt("1");
        proInfo.setProLx("2");
        save(proInfo);
        // 生成当前商品的中奖号码
        List<String> nums = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(proInfo.getProPrice()); i++) {
            nums.add(10000001 + i + "");
        }
        Collections.shuffle(nums);
        for (String num : nums) {
            redis.boundListOps(proInfo.getId() + "_nums").leftPush(num);

        }

        baseinfo.setProStore(storeNum - 1 + "");
        proBaseinfoService.update(baseinfo);
        return ApiResponse.success(MessageUtils.get("pro.groundSuc"));
    }

    @Override
    public ApiResponse<ProInfo> getLatestPerson(String id) {
        RuntimeCheck.ifBlank(id, MessageUtils.get("pro.idBlank"));
        ProInfo proInfo = baseMapper.getLatestPerson(id);
        if (proInfo != null) {
            User user = userService.findById(proInfo.getUserId());
            proInfo.setUserName(user.getUserName());
        }
        return ApiResponse.success(proInfo);
    }

    @Override
    public ApiResponse<ProInfo> getProInfo(String id) {
        RuntimeCheck.ifBlank(id, MessageUtils.get("pro.idBlank"));
        ProInfo proInfo = findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));
        String userId = (String) getAttribute("userId");
        List<String> nums = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.userid, userId);
            condition.eq(OrderList.InnerColumn.proId, id);
            List<OrderList> orderLists = orderListService.findByCondition(condition);
            int size = orderLists.size();
            proInfo.setCycs(size);
            if (CollectionUtils.isNotEmpty(orderLists)) {
                nums = orderLists.stream().map(OrderList::getNum).collect(Collectors.toList());
                proInfo.setNums(nums);
                proInfo.setCycs(nums.size());
            }
        }
        proInfo.setCycs(nums.size());
        setImgUrl(proInfo);
        // 获取上一期中奖的 商品id
        ProInfo info = baseMapper.getLatestPerson(proInfo.getProBaseid());
        if(info != null){
            SimpleCondition simpleCondition = new SimpleCondition(WinRecord.class);
            simpleCondition.eq(WinRecord.InnerColumn.proId, info.getId());
            List<WinRecord> winRecordList = winRecordService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(winRecordList)) {
                WinRecord record = winRecordList.get(0);
                record.setProName(proInfo.getProName());
                proInfo.setWinRecord(record);
            }
        }

        return ApiResponse.success(proInfo);
    }

    @Override
    public ApiResponse<String> getUserInfo(String id, int pageNum, int pageSize) {


        RuntimeCheck.ifBlank(id, MessageUtils.get("pro.idBlank"));

        ApiResponse<String> res = new ApiResponse<>();

        SimpleCondition condition = new SimpleCondition(Order.class);
        condition.eq(Order.InnerColumn.proId, id);
        condition.setOrderByClause(" cjsj desc ");
        PageInfo<Order> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            orderService.findByCondition(condition);
        });
        List<String> list = pageInfo.getList().stream().map(Order::getUserId).collect(Collectors.toList());
        List<User> users = userService.findByIds(list);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        pageInfo.getList().forEach(order -> {
            if (userMap.containsKey(order.getUserId())) {
                order.setUserName(userMap.get(order.getUserId()).getUserName());
            }
        });
        res.setPage(pageInfo);
        return res;
    }

    @Override
    public String getBaseId(String id) {
        String baseId = baseMapper.getBaseId(id);
        return baseId;
    }

    @Override
    public PageResponse<ProInfo> getRePager(int pageNum, int pageSize) {
        PageInfo<ProInfo> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            baseMapper.getRePager();
        });
        PageResponse<ProInfo> response = new PageResponse<>();
        response.setTotal(info.getTotal());
        response.setPageSize(pageSize);
        response.setPageNum(pageNum);
        for (ProInfo proInfo : info.getList()) {
            setImgUrl(proInfo);
        }
        response.setList(info.getList());
        return response;
    }

    @Override
    public ApiResponse<WinRecord> getLastestRecord(String id) {
        ProInfo proInfo = findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));
        ProInfo info = baseMapper.getLatestPerson(proInfo.getProBaseid());
        WinRecord record = null;
        if(info != null){
            SimpleCondition simpleCondition = new SimpleCondition(WinRecord.class);
            simpleCondition.eq(WinRecord.InnerColumn.proId, info.getId());
            List<WinRecord> winRecordList = winRecordService.findByCondition(simpleCondition);

            if (CollectionUtils.isNotEmpty(winRecordList)) {

                record = winRecordList.get(0);
                record.setProName(proInfo.getProName());
                if(StringUtils.isNotBlank(info.getUserId())){
                    User user = userService.findById(info.getUserId());
                    record.setHimg(user.gethImg());
                }
                return ApiResponse.success(record);
            }
        }

        return ApiResponse.success(record);
    }

    @Override
    public ApiResponse<List<String>> getMyNums(String id) {
        ProInfo proInfo = findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));
        List<String> nums = new ArrayList<>();
        String userId =  getHeader("userId");
        if (StringUtils.isNotBlank(userId)) {
            SimpleCondition condition = new SimpleCondition(OrderList.class);
            condition.eq(OrderList.InnerColumn.userid, userId);
            condition.eq(OrderList.InnerColumn.proId, id);
            List<OrderList> orderLists = orderListService.findByCondition(condition);
            int size = orderLists.size();
            proInfo.setCycs(size);
            if (CollectionUtils.isNotEmpty(orderLists)) {
                nums = orderLists.stream().map(OrderList::getNum).collect(Collectors.toList());
            }
        }
        return ApiResponse.success(nums);
    }

    @Override
    public PageResponse<WinRecord> winrecoeds(String id, int pageNum, int pageSize) {
        ProInfo proInfo = findById(id);
        RuntimeCheck.ifNull(proInfo, MessageUtils.get("pro.isNull"));
        SimpleCondition condition = new SimpleCondition(ProInfo.class);
        condition.eq(ProInfo.InnerColumn.proBaseid, proInfo.getProBaseid());
        condition.eq(ProInfo.InnerColumn.proZt, "4");
        condition.setOrderByClause(" kjsj desc ");
        PageInfo<ProInfo> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            findByCondition(condition);
        });
        List<ProInfo> list = info.getList();
        List<String> ids = list.stream().map(ProInfo::getId).collect(Collectors.toList());

        List<WinRecord> records = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            SimpleCondition wincon = new SimpleCondition(WinRecord.class);
            wincon.in(WinRecord.InnerColumn.proId, ids);
            wincon.setOrderByClause(" cjsj desc ");
            records = winRecordService.findByCondition(wincon);
            if(CollectionUtils.isNotEmpty(records)){
                records.forEach(
                        winRecord -> {
                            ProInfo info1 = proInfoService.findById(winRecord.getProId());
                            winRecord.setProName(info1.getProName());
                            User user = userService.findById(winRecord.getUserId());
                            winRecord.setHimg(user.gethImg());
                        }
                );
            }
        }


        PageResponse<WinRecord> page = new PageResponse<>();
        page.setList(records);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(info.getTotal());
        return page;
    }

    @Override
    public PageResponse<ProInfo> getNewPager(Page<ProInfo> page) {
        PageResponse<ProInfo> res= new PageResponse<>();
        LimitedCondition condition = getQueryCondition();
        PageInfo<ProInfo> info = findPage(page, condition);
        res.setPageNum(page.getPageNum());
        res.setPageSize(page.getPageSize());
        res.setList(info.getList());
        res.setTotal(info.getTotal());

        if(CollectionUtils.isNotEmpty(res.getList())){
            Set<String> set = res.getList().stream().map(ProInfo::getUserId).collect(Collectors.toSet());
            List<User> users = userService.findByIds(set);
            Map<String, User> userMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(users)){
                userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
            }
            Map<String, User> finalUserMap = userMap;
            res.getList().forEach(proInfo -> {
                if(StringUtils.isNotBlank(proInfo.getUserId()) && finalUserMap.containsKey(proInfo.getUserId())){
                    proInfo.setUserName(finalUserMap.get(proInfo.getUserId()).getUserName());
                }
                setImgUrl(proInfo);
            });

        }
        return res;
    }

    @Override
    public PageResponse<CyyhModel> getCyyh(int pageNum, int pageSize , String id) {

        PageResponse<CyyhModel> response = new PageResponse<>();
        List<CyyhModel> models = new ArrayList<>();
        // 获取所有购买当前商品的 用户已支付的订单
        SimpleCondition condition = new SimpleCondition(Order.class);
        condition.eq(Order.InnerColumn.proId , id);
        condition.notIn(Order.InnerColumn.ddzt, Arrays.asList("3", "5"));
        condition.setOrderByClause(" zfsj desc ");
        PageInfo<Order> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            orderService.findByCondition(condition);
        });
        List<Order> orders = pageInfo.getList();
        if(CollectionUtils.isNotEmpty(orders)){
            for (Order order : orders) {
                CyyhModel model = new CyyhModel();
                List<OrderList> lists = orderListService.findEq(OrderList.InnerColumn.orderId, order.getId());
                User user = userService.findById(order.getUserId());
                model.setGmfs(lists.size()+"");
                model.setGmsj(order.getZfsj());
                model.setHimg(user.gethImg());
                model.setUserId(user.getId());
                model.setUserName(user.getUserName());
                models.add(model);
            }
        }
        response.setTotal(pageInfo.getTotal());
        response.setList(models);
        response.setPageSize(pageSize);
        response.setPageNum(pageNum);


        return response;
    }

    @Override
    public PageResponse<CyyhModel> getInUser(String userId, int pageNum , int pageSize) {
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.idIsnull"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
        PageResponse<CyyhModel> res = new PageResponse<>();
        List<CyyhModel> models = new ArrayList<>();
        SimpleCondition condition = new SimpleCondition(Order.class);
        condition.eq(Order.InnerColumn.userId, userId);
        condition.setOrderByClause(" zfsj desc ");
        PageInfo<Order> objectPageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> orderService.findByCondition(condition));
        List<Order> orders = objectPageInfo.getList();
        if(CollectionUtils.isNotEmpty(orders)){
            Set<String> proIds = orders.stream().map(Order::getProId).collect(Collectors.toSet());
            List<ProInfo> infos = proInfoService.findByIds(proIds);
            // 拿到中奖用户的信息
            Map<String, ProInfo> infoMap = infos.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));
            Set<String> userIds = infos.stream().map(ProInfo::getUserId).collect(Collectors.toSet());
            List<User> users = userService.findByIds(userIds);
            Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
            for (Order order : orders) {
                CyyhModel model = new CyyhModel();

                model.setUserId(userId);
                model.setHimg(user.gethImg());
                model.setGmsj(order.getZfsj());
                model.setGmfs(order.getGmfs());
                model.setProName(order.getProName());
                model.setUserName(user.getUserName());
                model.setProId(order.getProId());
                model.setNum(order.getZjhm());
                if(infoMap.containsKey(order.getProId())){
                    ProInfo proInfo = infoMap.get(order.getProId());
                    model.setProPrice(proInfo.getProPrice());
                    model.setRePrice(proInfo.getRePrice());
                    model.setCoverUrl(proInfo.getCoverUrl());
                    if(userMap.containsKey(proInfo.getUserId())){
                        model.setWinName(userMap.get(order.getUserId()).getUserName());
                    }
                }
                models.add(model);
            }
        }
        res.setPageNum(pageNum);
        res.setPageSize(pageSize);
        res.setList(models);
        res.setTotal(objectPageInfo.getTotal());
        return res;
    }

    @Override
    public PageResponse<CyyhModel> getWin(String userId, int pageNum, int pageSize) {
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.idIsnull"));
        User user = userService.findById(userId);
        RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
        PageResponse<CyyhModel> res = new PageResponse<>();
        List<CyyhModel> models = new ArrayList<>();
        // 查询 此用户的所有中奖信息
        List<WinRecord> records = winRecordService.findEq(WinRecord.InnerColumn.userId, userId);
        Set<String> proIds = records.stream().map(WinRecord::getProId).collect(Collectors.toSet());
        SimpleCondition condition = new SimpleCondition(Order.class);
        condition.in(Order.InnerColumn.proId, proIds);
        condition.eq(Order.InnerColumn.userId, userId);
        condition.eq(Order.InnerColumn.ddzt, "1");
        condition.setOrderByClause(" zfsj desc ");
        PageInfo<Order> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> orderService.findByCondition(condition));
        List<Order> orders = info.getList();
        if(CollectionUtils.isNotEmpty(orders)){
            List<ProInfo> infos = proInfoService.findByIds(proIds);
            Map<String, ProInfo> infoMap = infos.stream().collect(Collectors.toMap(ProInfo::getId, p -> p));
            Set<String> userIds = infos.stream().map(ProInfo::getUserId).collect(Collectors.toSet());
            List<User> users = userService.findByIds(userIds);
            Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
            for (Order order : orders) {
                CyyhModel model = new CyyhModel();
                if(userMap.containsKey(order.getUserId())){
                    model.setWinName(userMap.get(order.getUserId()).getUserName());
                }
                model.setUserName(user.getUserName());
                model.setUserId(userId);
                model.setHimg(user.gethImg());
                model.setGmsj(order.getZfsj());
                model.setGmfs(order.getGmfs());
                model.setProName(order.getProName());
                model.setProId(order.getProId());
                model.setNum(order.getZjhm());
                if(infoMap.containsKey(order.getProId())){
                    ProInfo proInfo = infoMap.get(order.getProId());
                    model.setProPrice(proInfo.getProPrice());
                    model.setRePrice(proInfo.getRePrice());
                    model.setCoverUrl(proInfo.getCoverUrl());
                    if(userMap.containsKey(proInfo.getUserId())){
                        model.setWinName(userMap.get(order.getUserId()).getUserName());
                    }
                }
                models.add(model);
            }
        }
        res.setPageNum(pageNum);
        res.setPageSize(pageSize);
        res.setList(models);
        res.setTotal(info.getTotal());
        return res;
    }


    @Override
    public void afterPager(PageInfo<ProInfo> result) {
        List<ProInfo> list = result.getList();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (ProInfo proBaseinfo : list) {
            setImgUrl(proBaseinfo);
        }
    }

    @Override
    public void afterQuery(List<ProInfo> result) {

        for (ProInfo proBaseinfo : result) {
            setImgUrl(proBaseinfo);
        }
    }

    private void setImgUrl(ProInfo info) {
        List<String> imgUrls = new ArrayList<>();
        for (String s : info.getUrls().split(",")) {
            imgUrls.add(filePath + s);
        }
        info.setImgUrls(imgUrls);
        List<String> coverUrls = new ArrayList<>();
        for (String s : info.getCoverUrl().split(",")) {
            coverUrls.add(filePath + s);
        }
        info.setCoverUrls(coverUrls);
        List<String> refUrls = new ArrayList<>();
        for (String s : info.getRefUrl().split(",")) {
            refUrls.add(filePath + s);
        }
        info.setRefUrls(refUrls);
    }

    public static void main(String[] args) {


        List<String> nums = new ArrayList<>();
        for (int i = 0; i < 7699; i++) {
            nums.add(10000001 + i + "");
        }
        System.out.println(nums);
        Collections.shuffle(nums);
        System.out.println(nums);
        Collections.shuffle(nums);
        System.out.println(nums);

    }

}