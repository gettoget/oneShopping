package com.ldz.biz.controller;

import com.ldz.biz.model.Question;
import com.ldz.biz.service.QuestionService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionContrl extends BaseController<Question,String> {

    @Autowired
    private QuestionService service;


    @Override
    protected BaseService<Question, String> getBaseService() {
        return service;
    }


    @PostMapping("/update")
    public ApiResponse<String> update(Question entity){
        return service.updateEntity(entity);
    }

    /**
     *  查询聊天组
     * @param pageNum
     * @param pageSize
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/getPeoGroup")
    public ApiResponse<String> getPeoGroup(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize, String name, String startTime , String endTime){
            return service.getProGroup(pageNum,pageSize,name);
    }

    /**
     *   获取聊天记录
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @PostMapping("/getOneMess")
    public ApiResponse<String> getOneMess(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize,  String userId){
        return service.getOneMess(pageNum,pageSize,userId);
    }

}
