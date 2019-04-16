package com.ldz.biz.intercepter;

import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.YhService;
import com.ldz.util.spring.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AppInterceptor extends HandlerInterceptorAdapter {

    private GnService gnService;

    private YhService yhService;

    private SysYhJsMapper sysYhJsMapper;

    private StringRedisTemplate redisDao;


    public AppInterceptor() {
    }

    public AppInterceptor(StringRedisTemplate redisTemp) {
        this.gnService = SpringContextUtil.getBean(GnService.class);
        this.yhService = SpringContextUtil.getBean(YhService.class);
        this.sysYhJsMapper = SpringContextUtil.getBean(SysYhJsMapper.class);
        this.redisDao = redisTemp;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 查看请求类型
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            // 如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
            return true;
        }
        String userId = request.getHeader("userId");
        if (userId == null) {
            userId = request.getParameter("userId");
        }
        request.setAttribute("userId",userId);

        String imei = request.getHeader("imei");
        if (StringUtils.isBlank(imei)) {
            imei = request.getParameter("imei");
        }

        request.setAttribute("imei", imei);

        return super.preHandle(request, response, handler);
    }
}

