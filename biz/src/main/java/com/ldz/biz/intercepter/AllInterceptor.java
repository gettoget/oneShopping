package com.ldz.biz.intercepter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "commonDataFilter",urlPatterns = {"/*"})
public class AllInterceptor extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String lang = httpServletRequest.getHeader("lang");
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(httpServletRequest);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(lang)){
            requestWrapper.addParameter("lang", lang);
        }
        filterChain.doFilter(requestWrapper,httpServletResponse);
    }




    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        String lang = request.getHeader("lang");
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(lang)){
            requestWrapper.addParameter("lang", lang);
            request = (HttpServletRequest) requestWrapper.getRequest();
        }
        return super.preHandle(request,response,handler);
    }
*/
}
