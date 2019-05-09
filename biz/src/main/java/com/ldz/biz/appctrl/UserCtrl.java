package com.ldz.biz.appctrl;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import com.ldz.biz.model.UserModel;
import com.ldz.biz.service.UserService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ApiResponse<String> register(String phone, String password, String password1, String code){
        return service.register(phone, password,password1, code);
    }

    /**
     * 用户登录接口
     * @param phone 账号
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(String phone, String password){
        return  service.login(phone, password);
    }

    /**
     * 用户修改密码接口
     * @param pwd 原始密码
     * @param newPwd 新密码
     * @param newPwd1 确认
     * @return
     */
    @PostMapping("/editPwd")
    public ApiResponse<String> editPwd( String pwd, String newPwd, String newPwd1){
        return service.editPwd( pwd, newPwd, newPwd1);
    }

    /**
     * 修改用户信息接口
     * @param user 只能修改小部分信息
     * @return
     */
    @PostMapping("/editUserInfo")
    public ApiResponse<UserModel> editUserInfo(User user){
        return service.editUserInfo(user);
    }

    /**
     * 通过手机验证码修改密码
     * @param phone 手机号码
     * @param code 验证码
     * @param newPwd 新密码
     * @param newPwd1 确认密码
     * @return
     */
    @PostMapping("/findPwd")
    public ApiResponse<String> findPwd(String phone, String code, String newPwd, String newPwd1){
        return service.findPwd(phone, code,newPwd,newPwd1);
    }

    /**
     * 消费记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/myWallet")
    public PageResponse<PaymentBean> getMyWallet(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "8") int pageSize){
        return service.getMyWallet(pageNum,pageSize);
    }

    /**
     * 短信验证码下发接口
     * @param phone 用户手机号
     * @param type 类型  1 注册  2 找回登录密码 3 忘记支付密码
     * @return
     */
    @PostMapping("/sendMsg")
    public ApiResponse<String> sendMsg(String phone, String type){
        return service.sendMsg(phone, type);
    }

    /**
     * 我的号码
     * @param id
     * @return
     */
    @PostMapping("/myNum")
    public ApiResponse<List<String>> myNum(String id){
        return service.myNum(id);
    }

    /**
     * 支付密码设置
     *
     */
    @PostMapping("/savePayPwd")
    public ApiResponse<String> savePayPwd(String pwd, String pwd1){
        return service.savePayPwd(pwd,pwd1);
    }


    /**
     * 忘记支付密码找回
     */
    @PostMapping("/findPayPwd")
    public ApiResponse<String> findPayPwd(String newPwd,String newPwd1, String code){
        return service.findPayPwd(newPwd, newPwd1, code);
    }

    /**
     * 生成邀请码
     */
    @PostMapping("/genRefferCode")
    public ApiResponse<String> genRefferCode(){
        return service.genRefferCode();
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @PostMapping("/getUserInfo")
    public ApiResponse<UserModel> getUserInfo(){
        return service.getUserInfo();
    }


}
