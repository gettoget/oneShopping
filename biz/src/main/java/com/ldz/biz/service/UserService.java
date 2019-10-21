package com.ldz.biz.service;

import com.ldz.biz.model.PaymentBean;
import com.ldz.biz.model.User;
import com.ldz.biz.model.UserModel;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * @author slu
 */
public interface UserService extends BaseService<User, String> {
    /**
     * 用户注册
     * @param password
     * @param code
     * @param username
     * @return
     */
    ApiResponse<Map<String, Object>> register(String phone, String password, String password1, String code, String username);

    /**
     * 用户登录
     * @param phone
     * @param password
     * @return
     */
    ApiResponse<Map<String, Object>> login(String phone, String password) throws Exception;

    /**
     * 用户修改密码
     * @param pwd
     * @param newPwd
     * @param newPwd1
     * @return
     */
    ApiResponse<String> editPwd(String pwd, String newPwd, String newPwd1);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    ApiResponse<UserModel> editUserInfo(User user);

    /**
     * 重设密码
     * @param phone
     * @param code
     * @param newPwd
     * @param newPwd1
     * @return
     */
    ApiResponse<String> findPwd(String phone, String code, String newPwd, String newPwd1);

    /**
     * 消费记录分页接口
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<PaymentBean> getMyWallet(int pageNum, int pageSize);

    /**
     * 发送短信接口
     * @param phone
     * @param type
     * @return
     */
    ApiResponse<String> sendMsg(String phone, String type);

    /**
     * 我的号码
     * @param id
     * @return
     */
    ApiResponse<List<String>> myNum(String id);

    /**
     * 设置支付密码
     * @param pwd
     * @param pwd1
     * @return
     */
    ApiResponse<String> savePayPwd(String pwd, String pwd1);

    /**
     * 重设支付密码
     * @param newPwd
     * @param newPwd1
     * @param code
     * @return
     */
    ApiResponse<String> findPayPwd(String newPwd, String newPwd1, String code);

    /**
     * 获取推荐码
     * @return
     */
    ApiResponse<String> genRefferCode();

    /**
     * 获取当前登录用户信息
     * @return
     */
    ApiResponse<UserModel> getUserInfo();

    /**
     * 初始化所有的机器人用户
     */
    void initRobot();

    ApiResponse<String> updateEntity(User entity);

    ApiResponse<String> saveChannelId(String channelId);

    void saveBalance(String userId, String amount);

    ApiResponse<String> loginByCode(String phone, String code);
}