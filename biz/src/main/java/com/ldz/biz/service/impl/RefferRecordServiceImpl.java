package com.ldz.biz.service.impl;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
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
}