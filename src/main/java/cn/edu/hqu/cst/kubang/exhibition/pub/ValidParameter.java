package cn.edu.hqu.cst.kubang.exhibition.pub;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ValidParameter {
    //cn.edu.hqu.cst.kubang.exhibition包下所有的类
    @Pointcut("execution(* cn.edu.hqu.cst.kubang.exhibition..*.*(..)))")
    public void valid() {
    }

    @Around("valid()")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获得参数类型
        final Parameter[] parameters = method.getParameters();
        //参数值
        final Object[] args = joinPoint.getArgs();
        //参数名称
        String[] names = signature.getParameterNames();


        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object annotation = parameter.getAnnotation(NullDisable.class);
            //含有不为空的注解的参数
            if (null != annotation) {
                if (null == args[i]) {
                    throw new ParamException(String.format("参数:%s,不能为空", names[i]));
                }
            }

        }
        return joinPoint.proceed();
    }
}