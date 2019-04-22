package com.ldz.biz.appctrl;

import com.ldz.biz.model.ProEval;
import com.ldz.biz.service.ProEvalService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/proeval")
public class ProEvalCtrl extends BaseController<ProEval, String> {

    @Autowired
    private ProEvalService service;

    @Override
    protected BaseService<ProEval, String> getBaseService() {
        return service;
    }

    /**
     * 添加商品评论
     * @param entity
     * @return
     */
    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(ProEval entity){
        return service.saveEntity(entity);
    }



}
