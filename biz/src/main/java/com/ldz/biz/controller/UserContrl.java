package com.ldz.biz.controller;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserContrl extends BaseController<User,String> {

    @Autowired
    private UserService service;


    @Override
    protected BaseService<User, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/remove/{pkId}")
    public ApiResponse<String> remove(@PathVariable("pkId") String id){
        return ApiResponse.success();
    }


    @PostMapping("/update")
    public ApiResponse<String> update(User entity){
        return service.updateEntity(entity);
    }



}
