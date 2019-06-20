package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.QuestionMapper;
import com.ldz.biz.model.Question;
import com.ldz.biz.service.QuestionService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {

    @Autowired
    private QuestionMapper baseMapper;

    @Override
    protected Mapper<Question> getBaseMapper() {
        return  baseMapper;
    }

    @Override
    public ApiResponse<String> saveQuestion(Question entity) {
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(entity.getQue(), MessageUtils.get("que.isBlank"));
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        entity.setAns(null);
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setUserId(userId);
        save(entity);
        return ApiResponse.success();
    }

    @Override
    public PageResponse<Question> getNewPager(Page<Question> page) {
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        PageResponse<Question> res = new PageResponse<>();
        res.setPageNum(page.getPageNum());
        res.setPageSize(page.getPageSize());

        LimitedCondition condition = getQueryCondition();
        condition.eq(Question.InnerColumn.userId, userId);
        PageInfo<Question> info = findPage(page, condition);
        res.setList(info.getList());
        res.setTotal(info.getTotal());
        return res;
    }

    @Override
    public ApiResponse<String> delete(String id) {
        remove(id);
        return ApiResponse.success();
    }


}
