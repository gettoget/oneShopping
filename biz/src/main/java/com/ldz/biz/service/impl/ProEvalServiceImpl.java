package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.model.EvalCom;
import com.ldz.biz.model.User;
import com.ldz.biz.service.EvalComService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.ProEvalService;
import com.ldz.biz.mapper.ProEvalMapper;
import com.ldz.biz.model.ProEval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProEvalServiceImpl extends BaseServiceImpl<ProEval, String> implements ProEvalService {

	@Autowired
	private ProEvalMapper baseMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private EvalComService evalComService;

	@Override
	protected Mapper<ProEval> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> saveEntity(ProEval entity) {
		RuntimeCheck.ifBlank(entity.getProId(), MessageUtils.get("eval.proBlank"));
		RuntimeCheck.ifBlank(entity.getContent(), MessageUtils.get("eval.contentBlank"));
		String userId = getAttributeAsString("userId");
		User user = userService.findById(userId);
		RuntimeCheck.ifNull(user, MessageUtils.get("user.null"));

		entity.setUserId(userId);
		entity.setUserName(user.getUserName());
		entity.setId(genId());
		entity.setCjsj(DateUtils.getNowTime());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public PageResponse<ProEval> getNewPager(Page<ProEval> page) {
		LimitedCondition condition = getQueryCondition();
		PageInfo<ProEval> info = findPage(page, condition);
		PageResponse<ProEval> res = new PageResponse<>();
		if (CollectionUtils.isNotEmpty(info.getList())) {
			Set<String> userIds = info.getList().stream().map(ProEval::getUserId).collect(Collectors.toSet());
			List<User> users = userService.findByIds(userIds);
			Map<String,String> userMap = new HashMap<>();
			if(CollectionUtils.isNotEmpty(users)){
				userMap = users.stream().collect(Collectors.toMap(User::getId, p-> p.gethImg()));
			}
			String userId = getHeader("userId");
			List<String> evalIDs = info.getList().stream().map(ProEval::getId).collect(Collectors.toList());
			SimpleCondition simpleCondition = new SimpleCondition(EvalCom.class);
//			simpleCondition.eq(EvalCom.InnerColumn.userId,userId);
			simpleCondition.in(EvalCom.InnerColumn.evalId,evalIDs);
			List<EvalCom> coms = evalComService.findByCondition(simpleCondition);
			Map<String, String> finalUserMap = userMap;
			if(CollectionUtils.isNotEmpty(coms)){
				Map<String, List<EvalCom>> comMap = coms.stream().collect(Collectors.groupingBy(EvalCom::getEvalId));

				info.getList().forEach(proEval -> {
					if (comMap.containsKey(proEval.getId())){
						List<EvalCom> evalComs = comMap.get(proEval.getId());
						List<String> collect = evalComs.stream().map(EvalCom::getUserId).collect(Collectors.toList());
						if(collect.contains(userId)){
							proEval.setThumbs("1");
						}else{
							proEval.setThumbs("0");
						}

						proEval.setThumbsSum(evalComs.size());
					}else{
						proEval.setThumbs("0");
						proEval.setThumbsSum(0);
					}
					if(finalUserMap.containsKey(proEval.getUserId())){
						proEval.setHimg(finalUserMap.get(proEval.getUserId()));
					}
				});
			}else{
				info.getList().forEach(proEval -> {
					proEval.setThumbs("0");
					proEval.setThumbsSum(0);
					if (finalUserMap.containsKey(proEval.getUserId())) {
						proEval.setHimg(finalUserMap.get(proEval.getUserId()));
					}
				});
			}
		}

		res.setList(info.getList());
		res.setPageNum(page.getPageNum());
		res.setPageSize(page.getPageSize());
		res.setTotal(info.getTotal());
		return res;
	}

    @Override
    public ApiResponse<String> saveThumbs(String id) {
		ApiResponse<String> res = new ApiResponse<>();
        RuntimeCheck.ifBlank(id , MessageUtils.get("eval.idIsBlank"));
		String userId = getAttributeAsString("userId");
		RuntimeCheck.ifBlank(userId,  MessageUtils.get("user.notLogin"));
		ProEval eval = findById(id);
		SimpleCondition condition = new SimpleCondition(EvalCom.class);
		condition.eq(EvalCom.InnerColumn.evalId, id);
		condition.eq(EvalCom.InnerColumn.userId, userId);
		List<EvalCom> coms = evalComService.findByCondition(condition);
		if (CollectionUtils.isNotEmpty(coms)) {
			List<String> list = coms.stream().map(EvalCom::getId).collect(Collectors.toList());
			String[] ids = new String[list.size()];
			ids = list.toArray(ids);
			// 已点赞  取消点赞
			// 首先删除此点赞内容
			evalComService.removeIds(ids);
			// 点赞数 减一
			baseMapper.minusThumbs(id);
		}else{
			EvalCom evalCom = new EvalCom();
			evalCom.setId(genId());
			evalCom.setEvalId(id);
			evalCom.setUserId(userId);
			evalCom.setProId(eval.getProId());
			evalComService.save(evalCom);
			baseMapper.plusThumbs(id);
		}
		condition = new SimpleCondition(EvalCom.class);
		condition.eq(EvalCom.InnerColumn.evalId, id);
		Integer count = evalComService.countByCondition(condition);
		res.setResult(count+"");
		return res;
    }
}