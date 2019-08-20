package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.QueAnsMapper;
import com.ldz.biz.mapper.QuestionMapper;
import com.ldz.biz.model.QueAns;
import com.ldz.biz.model.Question;
import com.ldz.biz.model.User;
import com.ldz.biz.service.QuestionService;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.AndroidMsgBean;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.BaiduPushUtils;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {

    @Autowired
    private QuestionMapper baseMapper;

    @Autowired
    private QueAnsMapper queAnsMapper;

    @Autowired
    private RedisTemplateUtil redis;

    @Autowired
    private UserService userService;
    @Override
    protected Mapper<Question> getBaseMapper() {
        return  baseMapper;
    }

    @Override
    public ApiResponse<String> saveQuestion(Question entity) {
        String userId = getHeader("userId");
        RuntimeCheck.ifBlank(entity.getContent(), MessageUtils.get("que.isBlank"));
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.notLogin"));
        entity.setId(genId());
        entity.setType("1");
        entity.setCjsj(DateUtils.getNowTime());
        entity.setUserId(userId);
        entity.setHf("2");
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
        condition.setOrderByClause(" cjsj desc ");
        PageInfo<Question> info = findPage(page, condition);
        if(CollectionUtils.isNotEmpty(info.getList())){
            info.getList().sort(Comparator.comparing(Question::getCjsj));
        }
        res.setList(info.getList());
        res.setTotal(info.getTotal());
        return res;
    }

    @Override
    public ApiResponse<String> delete(String id) {
        remove(id);
        SimpleCondition  simpleCondition = new SimpleCondition(Question.class);
        simpleCondition.eq(Question.InnerColumn.parentId, id);
        baseMapper.deleteByExample(simpleCondition);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> updateEntity(Question entity) {
        String id = entity.getId();
        RuntimeCheck.ifBlank(id, MessageUtils.get("que.idBlank"));
        RuntimeCheck.ifBlank(entity.getContent(), MessageUtils.get("user.notLogin"));
        Question question = findById(id);
        question.setHf("1");
        update(question);

        Question que = new Question();
        que.setType("2");
        que.setContent(entity.getContent());
        que.setCjsj(DateUtils.getNowTime());
        que.setId(genId());
        que.setUserId(question.getUserId());
        save(que);

        QueAns queAns = new QueAns();
        queAns.setId(genId());
        queAns.setAnsId(que.getId());
        queAns.setQueId(question.getId());
        queAnsMapper.insert(queAns);

        // 客服回复 ， 推送消息
        AndroidMsgBean msgBean = new AndroidMsgBean();
        msgBean.setType("6");
        msgBean.setJson(JsonUtil.toJson(que));
        String channelId = (String) redis.boundValueOps(question.getUserId() + "_channelId").get();
        if (StringUtils.isNotBlank(channelId)) {
            BaiduPushUtils.pushSingleMsg(channelId,0,JsonUtil.toJson(msgBean),3);
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getProGroup(int pageNum, int pageSize, String name) {
        if (StringUtils.isBlank(name)){
            name = null;
        }
        String finalName = name;
        PageInfo<Question> info=
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> baseMapper.getPeoGroup(finalName));

        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> getOneMess(int pageNum, int pageSize, String userId) {
        RuntimeCheck.ifBlank(userId, MessageUtils.get("user.idIsnull"));
        PageInfo<Question>  info =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findEq(Question.InnerColumn.userId,
                        userId));
        ApiResponse<String> res = new ApiResponse<>();
        if(CollectionUtils.isNotEmpty(info.getList())){
            info.getList().sort(Comparator.comparing(Question::getCjsj).reversed());
        }
        res.setPage(info);
        return res;
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){

        String reply = getRequestParamterAsString("reply");
        if(StringUtils.isNotBlank(reply)){
            if(StringUtils.equals("1",reply)){
                condition.eq(Question.InnerColumn.hf, "1");
            }else if(StringUtils.equals("2",reply)){
                condition.eq(Question.InnerColumn.hf, "2");
            }else{
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
        String reply = getRequestParamterAsString("reply");
        Map<String,List<Question>> queMap = new HashMap<>();
        if(StringUtils.equals(reply, "1")){
            List<String> collect = list.stream().map(Question::getId).collect(Collectors.toList());
            SimpleCondition simpleCondition = new SimpleCondition(QueAns.class);
            simpleCondition.in(QueAns.InnerColumn.queId, collect);
            List<QueAns> queAns = queAnsMapper.selectByExample(simpleCondition);
            Map<String, List<String>> map = queAns.stream().collect(Collectors.groupingBy(QueAns::getQueId, Collectors.mapping(QueAns::getAnsId, Collectors.toList())));
            map.forEach((s, strings) -> {
                List<Question> questions = findByIds(strings);
                queMap.put(s,questions);
            });
        }

        Set<String> set = list.stream().map(Question::getUserId).collect(Collectors.toSet());
        List<User> users = userService.findByIds(set);
        Map<String,User> userMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(users)){
            userMap = users.stream().collect(Collectors.toMap(User::getId, p-> p));
        }
        Map<String, User> finalUserMap = userMap;
        list.forEach(question -> {
            if(queMap.containsKey(question.getId())){
                question.setReplyList(queMap.get(question.getId()));
            }
            if(finalUserMap.containsKey(question.getUserId())){
                User user = finalUserMap.get(question.getUserId());
                question.setHimg(user.gethImg());
                question.setUsername(user.getUserName());
            }
        });
    }




}
