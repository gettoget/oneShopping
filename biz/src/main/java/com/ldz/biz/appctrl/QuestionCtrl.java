package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Question;
import com.ldz.biz.service.QuestionService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/question")
public class QuestionCtrl {


    @Autowired
    private QuestionService service;


    /**
     * 添加留言
     */
    @PostMapping("/save")
    public ApiResponse<String> saveQuestion(Question entity){
        return service.saveQuestion(entity);
    }

    /**
     * 留言查询
     */
    @PostMapping("/newPager")
    public PageResponse<Question> newPager(Page<Question> page){
        return service.getNewPager(page);
    }

    /**
     * 留言删除
     */
    @PostMapping("/remove/{pkid}")
    public ApiResponse<String> delete(@PathVariable("pkid") String id){
        return service.delete(id);
    }


}
