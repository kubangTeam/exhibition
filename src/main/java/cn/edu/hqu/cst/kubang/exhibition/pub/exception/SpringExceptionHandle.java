package cn.edu.hqu.cst.kubang.exhibition.pub.exception;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.sql.SQLException;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 19:06
 * @Description:
 */

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class SpringExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(SpringExceptionHandle.class);

    //请求参数类型错误异常的捕获
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseJson<String> badRequest(BindException e) {
        logger.error("请求参数类型错误异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.BAD_REQUEST);
    }

    //404错误异常的捕获
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseJson<String> badRequestNotFound(BindException e) {
        logger.error("404错误异常： ", e.getMessage());
        return new ResponseJson<>(false, null, ResponseCodeEnums.NOT_FOUND);
    }

    //mybatis未绑定异常
    @ExceptionHandler(BindingException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson<String> mybatis(Exception e) {
        logger.error("mybatis未绑定异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.BOUND_STATEMENT_NOT_FOUNT);
    }

    //自定义异常的捕获 抛出异常。统一的在这里捕获返回JSON格式的友好提示。
    @ExceptionHandler(value = {UnicomRuntimeException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseJson<T> sendError(UnicomRuntimeException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("自定义异常： ", requestURI, exception.getMsg());
        return new ResponseJson<>(false, exception.getCode(), exception.getMsg());
    }

    //数据库操作出现异常
    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson<String> systemError(Exception e) {
        logger.error("数据库操作出现异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.DATABASE_ERROR);
    }

    //网络连接失败！
    @ExceptionHandler(value = {ConnectException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson<String> connect(Exception e) {
        logger.error("网络连接异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.CONNECTION_ERROR);
    }
    //参数异常
    @ExceptionHandler(value = {ParamException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseJson<String> errParam(Exception e) {
        logger.error("参数异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.ILLEGAL_ARGUMENT);
    }
    //其他异常
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseJson<String> notAllowed(Exception e) {
        logger.error("未知异常： ", e.getMessage());
        return new ResponseJson<>(false, ResponseCodeEnums.METHOD_NOT_ALLOWED);
    }
}
