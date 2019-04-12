package com.ldz.biz.controller;

import com.ldz.biz.model.RefferRecord;
import com.ldz.biz.service.RefferRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;

@RestController
@RequestMapping("/api/refferrecord")
public class RefferRecordCtrl extends BaseController<RefferRecord, String> {

    @Autowired
    private RefferRecordService service;

    @Override
    protected BaseService<RefferRecord, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(RefferRecord entity){
        return ApiResponse.success();
    }

    /**
     * 根据用户输入邀请码生成邀请记录
     * @param refCode
     * @return
     */
    @PostMapping("/saveRecord")
    public ApiResponse<String> saveRecord(String refCode){
        return service.saveRecord(refCode);
    }

    /**
     * 数据修改方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(RefferRecord entity){
        return ApiResponse.success();
    }

    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return ApiResponse.success();
    }



}
