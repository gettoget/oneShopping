package com.ldz.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.QuestionMapper;
import com.ldz.biz.model.Question;
import com.ldz.biz.model.User;
import com.ldz.biz.service.QuestionService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {

    @Autowired
    private QuestionMapper baseMapper;

    @Autowired
    private UserService userService;
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

    @Override
    public ApiResponse<String> updateEntity(Question entity) {
        String id = entity.getId();
        RuntimeCheck.ifBlank(id, "que.idBlank");
        Question question = findById(id);
        BeanUtil.copyProperties(entity,question, CopyOptions.create().setIgnoreError(true).setIgnoreNullValue(true).setIgnoreProperties("id","que","cjsj","userId"));
        update(question);
        return ApiResponse.success();
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){

        String reply = getRequestParamterAsString("reply");
        if(StringUtils.isNotBlank(reply)){
            if(StringUtils.equals(reply,"1")){
                condition.and().andIsNotNull(Question.InnerColumn.ans.name());
            }else if(StringUtils.equals(reply,"2")){
                condition.and().andIsNull(Question.InnerColumn.ans.name());
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterPager(PageInfo<Question> info){
        List<Question> list = info.getList();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        Set<String> set = list.stream().map(Question::getUserId).collect(Collectors.toSet());
        List<User> users = userService.findByIds(set);
        Map<String,User> userMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(users)){
            userMap = users.stream().collect(Collectors.toMap(User::getId, p-> p));
        }
        Map<String, User> finalUserMap = userMap;
        list.forEach(question -> {
            if(finalUserMap.containsKey(question.getUserId())){
                User user = finalUserMap.get(question.getUserId());
                question.setHimg(user.gethImg());
                question.setUsername(user.getUserName());
            }
        });
    }




}
