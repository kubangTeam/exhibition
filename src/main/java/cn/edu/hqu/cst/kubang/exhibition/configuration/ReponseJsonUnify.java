package cn.edu.hqu.cst.kubang.exhibition.configuration;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.18 20:03
 *  @Description:
 */

@ControllerAdvice(basePackages = "cn.edu.hqu.cst.kubang.exhibition.controller")
public class ReponseJsonUnify implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //System.out.println("----"+o.toString()+"type = "+o.getClass().toString());
        if (o.getClass().getName().equals("cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson"))
            return o;
//        System.out.println(o.getClass().getName());
        return new ResponseJson(true,null,null,o);
    }
}
