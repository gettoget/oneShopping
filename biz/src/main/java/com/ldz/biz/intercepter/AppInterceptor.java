package com.ldz.biz.intercepter;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.YhService;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.spring.SpringContextUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
@Log4j2
public class AppInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;

    private StringRedisTemplate redisDao;



    private List<String> whiteList = Arrays.asList("/app/user/login","/app/user/register","/app/user/sendMsg","/app/user/findPwd","/app/user/proeval/newPager");

    public AppInterceptor() {
    }

    public AppInterceptor(StringRedisTemplate redisTemp) {
        this.userService = SpringContextUtil.getBean(UserService.class);
        this.redisDao = redisTemp;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("接口请求地址 : {}" , request.getRequestURI());
        // 查看请求类型
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            // 如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
            return true;
        }
        if(StringUtils.startsWith(request.getRequestURI(),"/api/app/pay")){
            return true;
        }
        String userId = request.getHeader("userId");
        if (userId == null) {
            userId = request.getParameter("userId");
        }
        String token = request.getHeader("token");
        if(token == null){
            token =  request.getParameter("token");
        }

        String imei = request.getHeader("imei");
        if (StringUtils.isBlank(imei)) {
            imei = request.getParameter("imei");
        }
        if (StringUtils.isBlank(imei)) {
            String host = request.getRemoteAddr();
            imei = host.replaceAll("\\.","");
        }
        String requestURI = request.getRequestURI();
        if(StringUtils.startsWith(requestURI,"/app/guest") || whiteList.contains(requestURI)){
            if(StringUtils.isBlank(imei)){
                request.getRequestDispatcher("/authFiled").forward(request, response);
                return false;
            }else{
                request.setAttribute("imei", imei);
                return super.preHandle(request, response, handler);
            }
        }
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token) || StringUtils.isBlank(imei)){
            request.getRequestDispatcher("/authFiled").forward(request, response);
            return false;
        }

        User user = userService.findById(userId);
        if(user == null){
            request.getRequestDispatcher("/authFiled").forward(request, response);
            return false;
        }
        if(!user.getZt().equals("0")){
            request.getRequestDispatcher("/authFiled").forward(request, response);
            return false;
        }

        try {
            // 验证访问者是否合法
            String userid = JwtUtil.getClaimAsString(token, "userId");
            if (!userid.equals(userId)){
                request.getRequestDispatcher("/authFiled").forward(request, response);
                return false;
            }
            String value = redisDao.boundValueOps(userid).get();
            if (StringUtils.isEmpty(value) || !value.equals(token)){
                request.getRequestDispatcher("/authFiled").forward(request, response);
                return false;
            }
            request.setAttribute("userInfo", user);
            request.setAttribute("imei", imei);
            request.setAttribute("userId",userId);
        } catch (Exception e) {
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}

