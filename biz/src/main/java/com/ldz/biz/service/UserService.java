package com.ldz.biz.service;

import com.ldz.biz.model.User;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

/**
 * @author slu
 */
public interface UserService extends BaseService<User, String> {
    /**
     * 用户注册
     * @param username
     * @param password
     * @param code
     * @return
     */
    ApiResponse<String> register(String username, String password, String code);

    /**
     * 用户登录
     * @param phone
     * @param password
     * @return
     */
    ApiResponse<String> login(String phone, String password);

    /**
     * 用户修改密码
     * @param phone
     * @param pwd
     * @param newPwd
     * @param newPwd1
     * @return
     */
    ApiResponse<String> editPwd(String phone, String pwd, String newPwd, String newPwd1);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    ApiResponse<String> editUserInfo(User user);

    /**
     * 重设密码
     * @param phone
     * @param code
     * @param newPwd
     * @param newPwd1
     * @param type
     * @return
     */
    ApiResponse<String> findPwd(String phone, String code, String newPwd, String newPwd1, String type);

    /**
     * 消费记录分页接口
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiResponse<String> getMyWallet(int pageNum, int pageSize);

    /**
     * 发送短信接口
     * @param phone
     * @param type
     * @return
     */
    ApiResponse<String> sendMsg(String phone, String type);
}