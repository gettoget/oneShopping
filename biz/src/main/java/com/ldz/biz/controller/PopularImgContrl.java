package com.ldz.biz.controller;

import com.ldz.biz.model.Popularimgs;
import com.ldz.biz.service.PopularimgsService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/popularimgs")
public class PopularImgContrl extends BaseController<Popularimgs,String> {

    @Autowired
    private PopularimgsService service;

    @Override
    protected BaseService<Popularimgs, String> getBaseService() {
        return service;
    }
    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        service.removeEntity(id);
        return ApiResponse.success();
    }
    /**
     * 保存推荐轮播图
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    @Override
    public ApiResponse<String> save(Popularimgs entity){
        return service.saveEntity(entity);
    }

}
