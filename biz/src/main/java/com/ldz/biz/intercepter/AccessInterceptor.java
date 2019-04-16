package com.ldz.biz.intercepter;

import com.ldz.biz.model.User;
import com.ldz.biz.service.UserService;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.model.SysGn;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.YhService;
import com.ldz.util.commonUtil.JwtUtil;
import com.ldz.util.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 访问接口控制
 *
 * @author 李彬彬
 *
 */
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

	private GnService gnService;

	private YhService yhService;

	private SysYhJsMapper sysYhJsMapper;

	private StringRedisTemplate redisDao;

	// 只要登录的用户都能访问

	public AccessInterceptor() {
	}

	public AccessInterceptor(StringRedisTemplate redisTemp) {
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

		// 测试代码
		// 访问权限值
		// String userid = "1";
		// String token =
		// "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiYWRtaW5pIiwiaXNzIjoid2NwbXMiLCJ1c2VySWQiOiIxIn0.vok82zo-zveVlXrjKxgJiRRdXqKGpv1PFBngxhyR-Cg";
		String userid = request.getHeader("userid");
		String token = request.getHeader("token");
		String url = request.getHeader("url");

		if (token == null)
		{
			token = request.getParameter("token");
		}
		if (userid == null)
		{
			userid = request.getParameter("userid");
		}

		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)){
			request.getRequestDispatcher("/authFiled").forward(request, response);
			return false;
		}
		log.debug("访问地址[{}]", request.getRequestURI());

		// 验证用户状态
		SysYh user = yhService.findById(userid);
		if (!Dict.UserStatus.VALID.getCode().equals(user.getZt())){
			request.getRequestDispatcher("/authFiled").forward(request, response);
			return false;
		}
		try {
			// 验证访问者是否合法
			String userId = JwtUtil.getClaimAsString(token, "userId");
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
			request.setAttribute("orgCode", user.getJgdm());
			request.setAttribute("orgCodes",user.getJgdms());

		} catch (Exception e) {
			return false;
		}

		return super.preHandle(request, response, handler);
	}





}
