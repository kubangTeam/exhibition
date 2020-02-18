package cn.edu.hqu.cst.kubang.exhibition.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.18 00:47
 *  @Description: 禁止参数为空
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
public @interface NullDisable {

}