package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.RefferRecordService;
import com.ldz.biz.mapper.RefferRecordMapper;
import com.ldz.biz.model.RefferRecord;

import java.util.List;

@Service
public class RefferRecordServiceImpl extends BaseServiceImpl<RefferRecord, String> implements RefferRecordService {

	@Autowired
	private RefferRecordMapper baseMapper;

	@Autowired
	private UserService userService;

	@Override
	protected Mapper<RefferRecord> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> saveRecord(String refCode) {
		RuntimeCheck.ifBlank(refCode, MessageUtils.get("ref.codeBlank"));
		SimpleCondition condition = new SimpleCondition(User.class);
		condition.eq(User.InnerColumn.refCode, refCode);
		List<User> users = userService.findByCondition(condition);
		RuntimeCheck.ifTrue(CollectionUtils.isEmpty(users), MessageUtils.get("ref.userIsNull"));

		RefferRecord record = new RefferRecord();
		record.setId(genId());
		User u = users.get(0);
		record.setUserId(u.getId());
		record.setUserName(u.getUserName());
		String userId = getAttributeAsString("userId");
		User user = userService.findById(userId);
		record.setApplyId(user.getId());
		record.setApplyName(user.getUserName());
		record.setCjsj(DateUtils.getNowTime());
		save(record);
		return ApiResponse.success();
    }

    @Override
    public PageResponse<RefferRecord> getNewPager(Page<RefferRecord> page) {

		PageResponse<RefferRecord> res = new PageResponse<>();
		String userId = getAttributeAsString("userId");
		if(StringUtils.isNotBlank(userId)){
			LimitedCondition condition = getQueryCondition();
			condition.eq(RefferRecord.InnerColumn.userId, userId);
			PageInfo<RefferRecord> info = findPage(page, condition);
			res.setPageNum(page.getPageNum());
			res.setPageSize(page.getPageSize());
			res.setList(info.getList());
			res.setTotal(info.getTotal());
		}
		return res;
    }
}