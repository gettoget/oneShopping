package com.ldz.biz.intercepter;

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
	private List<String> whiteList = Arrays.asList("/api/traineeinformation/pager","/api/jg/getOrgPath","/api/gn/getPermissionTreeWithChecked","/api/gn/getRolePermissionTree","/api/jg/getCurrentOrgTree","/api/gn/getMenuTree","/api/jg/pager","/api/zd/pager","/api/jg/getTree","/api/gn/getMenuTree","/api/jg/pager","/api/jg/getOrgTree","/api/jg/getOrgTree","/api/clsbyxsjjl/history","/api/clsbyxsjjl/history","/api/jg/getCurrentUserOrgTree","/api/yh/mdfPwd","/api/traineeinformation/exportTestCharge");

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

		return super.preHandle(request, response, handler);
	}




}
