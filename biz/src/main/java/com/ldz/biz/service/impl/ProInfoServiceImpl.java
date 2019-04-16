package com.ldz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.mapper.ProInfoMapper;

import java.util.List;
import java.util.Map;
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


	@Override
	protected Mapper<ProInfo> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> saveOne(String id) {
		ProBaseinfo baseinfo = proBaseinfoService.findById(id);
		RuntimeCheck.ifNull(baseinfo, MessageUtils.get("pro.isNull"));
		int storeNum = Integer.parseInt(baseinfo.getProStore());
		if(storeNum <= 0 ){
			return ApiResponse.fail(MessageUtils.get("pro.storeIsNull"));
		}

		ProInfo  proInfo = new ProInfo(baseinfo);
		proInfo.setId(genId());
		proInfo.setCjsj(DateUtils.getNowTime());
		proInfo.setCyyhs("0");
		proInfo.setGxsj(proInfo.getCjsj());
		proInfo.setProStore("1");
		proInfo.setProZt("1");
		save(proInfo);

		baseinfo.setProStore(storeNum -1 + "");
		proBaseinfoService.update(baseinfo);
		return ApiResponse.success(MessageUtils.get("pro.groundSuc"));
	}

    @Override
    public ApiResponse<ProInfo> getLatestPerson(String id) {
        RuntimeCheck.ifBlank(id, MessageUtils.get("pro.idBlank"));
		ProInfo proInfo = baseMapper.getLatestPerson(id);
		if(proInfo != null){
			User user = userService.findById(proInfo.getUserId());
			proInfo.setUserName(user.getUserName());
		}
		return ApiResponse.success(proInfo);
    }

	@Override
	public ApiResponse<ProInfo> getProInfo(String id) {
		RuntimeCheck.ifBlank(id, MessageUtils.get("pro.idBlank"));
		ProInfo proInfo = findById(id);
		String userId = (String) getAttribute("userId");
		if(StringUtils.isNotBlank(userId)){
			SimpleCondition condition = new SimpleCondition(OrderList.class);
			condition.eq(OrderList.InnerColumn.userid, userId);
			condition.eq(OrderList.InnerColumn.proId, id);
			List<OrderList> orderLists = orderListService.findByCondition(condition);
			int size = orderLists.size();
			proInfo.setCycs(size);
			// 获取上一期中奖的 商品id
			ProInfo info = baseMapper.getLatestPerson(proInfo.getProBaseid());
			SimpleCondition simpleCondition = new SimpleCondition(WinRecord.class);
			simpleCondition.eq(WinRecord.InnerColumn.proId, info.getId());
			List<WinRecord> winRecordList = winRecordService.findByCondition(simpleCondition);
			if(CollectionUtils.isNotEmpty(winRecordList)){
				proInfo.setWinRecord(winRecordList.get(0));
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
			if(userMap.containsKey(order.getUserId())){
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
}