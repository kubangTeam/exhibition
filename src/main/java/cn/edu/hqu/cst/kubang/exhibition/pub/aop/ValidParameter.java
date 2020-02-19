package cn.edu.hqu.cst.kubang.exhibition.pub.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.pub.exception.ParamException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 16:04
 * @Description: AOP 拦截器方式拦截注解标签，实现公共方法,抛出异常统一处理
 */

@Aspect
@Component
public class ValidParameter {
    @Pointcut("execution(* cn.edu.hqu.cst.kubang.exhibition..*.*(..)))")
    public void valid() {}

    @Before("valid()")
    public void check(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        final Object[] values = joinPoint.getArgs();
        final String[] names = signature.getParameterNames();
        Annotation nullDisable = method.getAnnotation(NullDisable.class);
        //如果该方法被@NullDisable修饰，那就要检查参数
        if (null != nullDisable) {
            for (int i = 0; i < names.length; i++) {
                System.out.println("@NullDisable: " + names[i] + ": " + values[i]);
                //循环扫描所有参数，如果第i个参数为空
                if (StringUtils.isEmpty(values[i])) {
                    throw new ParamException("参数 '" + names[i] + "' 不能为空！");
                }
            }
        }
    }
}