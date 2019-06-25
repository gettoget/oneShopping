package com.ldz.biz.intercepter;

import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.commonUtil.MessageUtils;
import com.ldz.util.commonUtil.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        System.out.println("111 ");
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        URI uri = serverHttpRequest.getURI();
        if(StringUtils.startsWith(uri.getPath(),"/app/")){
            String rsa="";
            try {
                rsa = RSAUtils.encryptWithRSA(JsonUtil.toJson(o));
            } catch (Exception e) {
                MessageUtils.get("error");
            }
            return rsa;
        }else {
           return o;
        }

//       return o;
    }
}
