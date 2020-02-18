package cn.edu.hqu.cst.kubang.exhibition.pub.aop;

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
 *  @author: 邢佳成
 *  @Date: 2020.02.18 16:04
 *  @Description: AOP 拦截器方式拦截注解标签，实现公共方法
 */

@Aspect
@Component
public class ValidParameter {
    //cn.edu.hqu.cst.kubang.exhibition包下所有的类
    @Pointcut("execution(* cn.edu.hqu.cst.kubang.exhibition..*.*(..)))")
    public void valid() {}

    @Before("valid()")
    public void check(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获得参数类型
        final Parameter[] parameters = method.getParameters();
        //参数值
        final Object[] args = joinPoint.getArgs();
        //参数名称
        String[] names = signature.getParameterNames();

        Object annotation = method.getAnnotation(NullDisable.class);
        //如果该方法被@NullDisable修饰，那就要检查参数
        if (null != annotation){
            for (int i = 0; i < parameters.length; i++){
                System.out.println("--"+names[i]+": "+args[i]);
                //循环扫描所有参数，如果第i个参数为空
                if (StringUtils.isEmpty(args[i])){
                    throw new ParamException("参数 '"+names[i]+"' 不能为空！");
                }
            }
        }
    }
}