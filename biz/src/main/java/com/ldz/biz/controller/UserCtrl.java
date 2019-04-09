package com.ldz.biz.controller;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user")
public class UserCtrl  {

    @Autowired
    private UserService service;

    /**
     * 用户注册接口
     * @param phone  手机号码
     * @param password 密码
     * @param code 短信验证码
     * @return
     */
    @PostMapping("/register")
    public ApiResponse<String> register(String phone, String password, String code){
        return service.register(phone, password, code);
    }

    /**
     * 用户登录接口
     * @param phone 账号
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<String> login(String phone, String password){
        return  service.login(phone, password);
    }

    /**
     * 用户修改密码接口
     * @param phone 账号
     * @param pwd 原始密码
     * @param newPwd 新密码
     * @param newPwd1 确认
     * @return
     */
    @PostMapping("/editPwd")
    public ApiResponse<String> editPwd(String phone, String pwd, String newPwd, String newPwd1){
        return service.editPwd(phone, pwd, newPwd, newPwd1);
    }

    /**
     * 修改用户信息接口
     * @param user 只能修改小部分信息
     * @return
     */
    @PostMapping("/editUserInfo")
    public ApiResponse<String> editUserInfo(User user){
        return service.editUserInfo(user);
    }

    /**
     * 通过手机验证码修改密码
     * @param phone 手机号码
     * @param code 验证码
     * @param newPwd 新密码
     * @param newPwd1 确认密码
     * @param type 1 验证短信验证码 2 修改密码
     * @return
     */
    @PostMapping("/findPwd")
    public ApiResponse<String> findPwd(String phone, String code, String newPwd, String newPwd1, String type){
        return service.findPwd(phone, code,newPwd,newPwd1,type);
    }

    @PostMapping("/myWallet")
    public ApiResponse<String> getMyWallet(){
        return service.getMyWallet();
    }



}
