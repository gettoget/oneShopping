package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ExchangeMapper;
import com.ldz.biz.model.Exchange;
import com.ldz.biz.model.User;
import com.ldz.biz.service.ExchangeService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.print.attribute.standard.PagesPerMinuteColor;

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
    public PageResponse<Exchange> getNewPager(Page<Exchange> page) {
        PageResponse<Exchange> res = new PageResponse<>();

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
}