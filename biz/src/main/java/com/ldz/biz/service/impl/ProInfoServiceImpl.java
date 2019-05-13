package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.bean.ProInfoLuckNumBean;
import com.ldz.biz.mapper.OrderListMapper;
import com.ldz.biz.mapper.OrderMapper;
import com.ldz.biz.mapper.ProInfoMapper;
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
import com.ldz.util.exception.RuntimeCheckException;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

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
    
    @Autowired
	private OrderListMapper orderListMapper;
    @Autowired
	private OrderMapper orderMapper;
    

    @Value("${robot.point}")
    private double point;
    @Value("${robot.people}")
    private int people;
    @Value("${robot.lowsize}")
    private int lowsize;

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
            ProInfoLuckNumBean numBean = new ProInfoLuckNumBean();
            numBean.setProId(proInfo.getId());
            numBean.setProName(proInfo.getProName());
            numBean.setLuckNum(num);
            redis.boundSetOps(proInfo.getId() + "_nums").add(numBean);
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
            if(StringUtils.isNotBlank(proInfo.getUserId())){
                User user = userService.findById(proInfo.getUserId());
                proInfo.setUserName(user.getUserName());
            }
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
                    simpleCondition = new SimpleCondition(OrderList.class);
                    simpleCondition.eq(OrderList.InnerColumn.proId, info.getId());
                    simpleCondition.eq(OrderList.InnerColumn.userid, user.getId());
                    List<OrderList> orderLists = orderListService.findByCondition(simpleCondition);
                    if (CollectionUtils.isNotEmpty(orderLists)) {
                        List<String> nums = orderLists.stream().map(OrderList::getNum).sorted(String::compareTo).collect(Collectors.toList());
                        record.setNums(nums);
                    }
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
            Set<String> proIds = res.getList().stream().map(ProInfo::getId).collect(Collectors.toSet());
            List<WinRecord> records = winRecordService.findIn(WinRecord.InnerColumn.proId, proIds);
            Map<String, String> map = records.stream().collect(Collectors.toMap(WinRecord::getProId, p -> p.getZjfs()));
            if(CollectionUtils.isNotEmpty(set)) {
                List<User> users = userService.findByIds(set);
                Map<String, User> userMap = new HashMap<>();
                if (CollectionUtils.isNotEmpty(users)) {
                    userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
                }
                Map<String, User> finalUserMap = userMap;
                res.getList().forEach(proInfo -> {
                    if (StringUtils.isNotBlank(proInfo.getUserId()) && finalUserMap.containsKey(proInfo.getUserId())) {
                        proInfo.setUserName(finalUserMap.get(proInfo.getUserId()).getUserName());
                    }
                    if(map.containsKey(proInfo.getId())){
                        proInfo.setZjfs(map.get(proInfo.getId()));
                    }
                    setImgUrl(proInfo);
                });
            }else{
                res.getList().forEach(proInfo -> {
                    setImgUrl(proInfo);
                });
            }

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
                if(StringUtils.isNotBlank(order.getUserId())){
                    User user = userService.findById(order.getUserId());
                    model.setHimg(user.gethImg());
                    model.setUserId(user.getId());
                    model.setUserName(user.getUserName());
                }
                model.setGmfs(lists.size()+"");
                model.setGmsj(order.getZfsj());

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
    public ApiResponse<String> updateKj(String id) {
        orderService.fenpei(id);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> delLuckNums() {
        Set<Object> keys = redis.keys("*_nums");
        for (Object key : keys) {
            String[] s = key.toString().split("_");
            ProInfo info = findById(s[0]);
            if(info == null){
                redis.delete(key);
            }
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> initRobot() {
        userService.initRobot();
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> saveAll() {
        List<ProBaseinfo> all = proBaseinfoService.findAll();
        all.forEach(baseinfo -> proInfoService.saveOne(baseinfo.getId()));
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getLatestPro(String id) {
        String s = baseMapper.getLatestPro(id);
        return ApiResponse.success(s);
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

    @Override
    public void saveRobot(String redisProInfoKey) {
    	String proId = redisProInfoKey.toString().split("_")[0];
        //1.生成本次多少个用户参与
        int randomMaxUserNum = RandomUtils.nextInt(people);
        if (randomMaxUserNum == 0) {
            return;
        }
        //2.商品剩余名额。将剩余名额分配给用户
        int allocNum = 0;
		long proSyNum = redis.boundSetOps(redisProInfoKey).size();
		if (proSyNum == 0){
			//商品名额已经用完
			return;
		}else if (proSyNum <= lowsize){
			//如果商品剩下最后设定的份数，一次性全分给用户
			allocNum = (int)proSyNum;
		}else{
			//随机生成本次消费商品份数
            int randomMaxNum = Math.max(1, (int) (proSyNum * point));
            allocNum = RandomUtils.nextInt(randomMaxNum);
            if (allocNum == 0) {
            	allocNum = 1;
            }
		}
		List<Object> elements = new ArrayList<>();
		List<Object> cloneElements = new ArrayList<>();
		// 随机提取中奖号码
		Set<Object> luckNums = redis.boundSetOps(redisProInfoKey).distinctRandomMembers(allocNum);
		// 删除redis的中奖号码
        Iterator<Object> removeNum = luckNums.iterator();
        while(removeNum.hasNext()){
        	Object element = removeNum.next();
        	// 防止并发操作时，中奖号码被重复分。先进行删除，redis删除结果不为0，表示号码可用
            long removeFlag = redis.boundSetOps(redisProInfoKey).remove(element);
            if (removeFlag > 0){
            	elements.add(element);
            }
        }
        // 防止因并发抢购，造成随机element中奖号码没有获取成功
        if (elements.size() == 0){
        	return;
        }else{
        	CollectionUtils.addAll(cloneElements, elements);
        }
        
        try{
        	//3.从redis随机获取机器人用户
    		Set<Object> users = redis.boundSetOps(User.class.getName()).distinctRandomMembers(randomMaxUserNum);
    		
            Iterator<Object> iteUsers = users.iterator();
            int tmpNum = allocNum;
            List<OrderList> orderLists = new ArrayList<>();
            List<Order> orders = new ArrayList<>();
            List<User> joinUsers = new ArrayList<>();
            int allocUserIndex = 0;
        	while(iteUsers.hasNext()){
        		User user = (User)iteUsers.next();
        		joinUsers.add(user);
        		//4.参与的用户,每个用户随机分配商品份数.最终随机的份数不超过本次消费的商品份数
                //获取单个用户消费份数
                int pUserNum = RandomUtils.nextInt(tmpNum);
                if (allocUserIndex == (randomMaxUserNum - 1)) {
                    pUserNum = tmpNum;
                } else if (pUserNum == 0 && tmpNum > 0) {
                    pUserNum = 1;
                }
                if (pUserNum >= elements.size()){
                	pUserNum = elements.size();
                }
                //用户消费记录,获取中奖号码
                List<Object> tmpList = elements.subList(0, pUserNum);
                ProInfoLuckNumBean proInfo = (ProInfoLuckNumBean)tmpList.get(0);
                // 生成订单
                Order order = new Order();
                order.setUserName(user.getUserName());
                order.setZfsj(DateUtils.getNowTime());
                order.setId(genId());
                order.setOrderType("2");
                order.setProId(proId);
                order.setProName(proInfo.getProName());
                order.setUserId(user.getId());
                order.setGmfs(pUserNum+"");
                order.setZfje(pUserNum+"");
                order.setCjsj(DateUtils.getNowTime());
                order.setImei(user.getRegImei());
                order.setDdzt("0");
                
                for (int j = 0; j < tmpList.size(); j++) {
                	ProInfoLuckNumBean tmpItem = (ProInfoLuckNumBean)tmpList.get(j);
                    String luckNum = tmpItem.getLuckNum();
                    OrderList orderList = new OrderList();
                    orderList.setId(genId());
                    orderList.setOrderId(order.getId());
                    orderList.setProId(proId);
                    orderList.setProName(tmpItem.getProName());
                    orderList.setUserid(user.getId());
                    orderList.setUserName(user.getUserName());
                    orderList.setYhlx(user.getSource());
                    orderList.setNum(luckNum);
                    //执行加一下随机数，防止时间毫秒数都一致
                    int randomMillis = RandomUtils.nextInt(100);
                    orderList.setCjsj(DateTime.now().plusMillis(randomMillis).toString("yyyy-MM-dd HH:mm:ss.SSS"));
                    orderLists.add(orderList);
                }
                orders.add(order);
                //扣减本次消费份数剩余额度
                tmpNum -= pUserNum;
                elements.removeAll(tmpList);
                allocUserIndex++;
                if (tmpNum <= 0) {
                    break;
                }
        	}
        	
        	// 产品分配完成 更新 添加 sql
        	orderListMapper.insertList(orderLists);
            orderMapper.insertList(orders);
            int updateNum = orderMapper.minusRePrice(allocNum, proId);
            if (updateNum == 0){
            	throw new RuntimeCheckException(MessageUtils.get("pro.minusFail"));
            }
            //如果商品已卖完，则更新商品状态
            if (redis.boundSetOps(redisProInfoKey).size() == 0){
            	// 更新商品状态为 待开奖
                orderMapper.updateFinish(proId);
            	// 号码分配完 清理redis key
                redis.delete(proId + "_nums");
                // 将待开奖的商品持久化存储起来，防止服务异常停止，造成商品无法开奖。同时建立延时任务 , 分配中奖号码
                // 商品结束后1分钟执行开奖功能
                long millis = DateTime.now().plusMinutes(1).getMillis();
                redis.boundZSetOps(ProInfo.class.getSimpleName()+"_award").add(proId, millis);
            }
        }catch(Exception e){
        	if (cloneElements.size() > 0){
        		// 执行发生异常后，需要先将中奖号码回压到redis中，防止号码丢失
        		for (int i=0; i<cloneElements.size(); i++){
        			redis.boundSetOps(redisProInfoKey).add(cloneElements.get(i));
        		}
        	}
        	// 继续抛出异常，保证数据库事务回滚
        	throw e;
        }
    }
}