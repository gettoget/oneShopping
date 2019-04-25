package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ProEval;
import com.ldz.biz.service.ProEvalService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/proeval")
public class ProEvalCtrl  {

    @Autowired
    private ProEvalService service;



    /**
     * 添加商品评论
     * @param entity
     * @return
     */

    @PostMapping("/save")
    public ApiResponse<String> save(ProEval entity){
        return service.saveEntity(entity);
    }

    /**
     * 查看评论
     */
    @GetMapping("/newPager")
    public PageResponse<ProEval> getNewPager(Page<ProEval> page){
        return service.getNewPager(page);
    }



}
