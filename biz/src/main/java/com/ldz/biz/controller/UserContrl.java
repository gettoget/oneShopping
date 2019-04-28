package com.ldz.biz.controller;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import com.ldz.biz.model.UserModel;
import com.ldz.biz.service.UserService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserContrl extends BaseController<User,String> {

    @Autowired
    private UserService service;


    @Override
    protected BaseService<User, String> getBaseService() {
        return service;
    }
}
