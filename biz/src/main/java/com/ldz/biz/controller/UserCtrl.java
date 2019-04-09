package com.ldz.biz.controller;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserCtrl extends BaseController<User, String> {

    @Autowired
    private UserService service;

    @Override
    protected BaseService<User, String> getBaseService() {
        return service;
    }
}
