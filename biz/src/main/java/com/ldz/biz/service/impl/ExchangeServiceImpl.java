package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.model.Exchange;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.model.User;
import com.ldz.biz.service.ExchangeService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl extends BaseServiceImpl<Exchange, String> implements ExchangeService {

	@Autowired
	private ExchangeMapper baseMapper;
	@Autowired
	private UserService userService;
	
	@Override
	protected Mapper<Exchange> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public boolean fillPagerCondition(LimitedCondition condition){

		String username = getRequestParamterAsString("username");
		if(StringUtils.isNotBlank(username)){
			List<User> users = userService.findLike(User.InnerColumn.userName, username);
			Set<String> userid = users.stream().map(User::getId).collect(Collectors.toSet());
			condition.in(Recharge.InnerColumn.userId, userid);
		}
		return true;
	}

    @Override
    public PageResponse<Exchange> getNewPager(Page<Exchange> page) {
        PageResponse<Exchange> res = new PageResponse<>();
		// 用户需要登录才能查询
		String userId = getAttributeAsString("userId");
		if(StringUtils.isNotBlank(userId)){
			User user = userService.findById(userId);
			RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));
			LimitedCondition condition = getQueryCondition();
			condition.eq(Exchange.InnerColumn.userid, userId);
			PageInfo<Exchange> info = findPage(page, condition);
			res.setTotal(info.getTotal());
			res.setPageSize(page.getPageSize());
			res.setPageNum(page.getPageNum());
			res.setList(info.getList());
		}
		return res;
    }



	@Override
	public void afterPager(PageInfo<Exchange> info){
		List<Exchange> list = info.getList();
		if(CollectionUtils.isEmpty(list)){
			return;
		}

		Set<String> set = list.stream().map(Exchange::getUserid).collect(Collectors.toSet());
		List<User> users = userService.findByIds(set);
		Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getId, p -> p));
		list.forEach(recharge -> {
			if(userMap.containsKey(recharge.getUserid())){
				User user = userMap.get(recharge.getUserid());
				recharge.setUsername(user.getUserName());
				recharge.setHimg(user.gethImg());
			}
		});
	}
}