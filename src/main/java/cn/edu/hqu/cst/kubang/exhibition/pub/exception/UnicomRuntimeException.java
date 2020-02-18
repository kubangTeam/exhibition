package cn.edu.hqu.cst.kubang.exhibition.pub.exception;

import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 18:58
 * @Description:
 */

public class UnicomRuntimeException extends RuntimeException{
    private static final long serialVersionUID = -964594797020452508L;
    protected String code;

    protected String msg;

    protected String message;//打印出的日志信息

    public UnicomRuntimeException(ResponseCodeEnums enums, String message) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public UnicomRuntimeException(ResponseCodeEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public UnicomRuntimeException() {
        super();
    }

    public UnicomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnicomRuntimeException(String message) {
        super(message);
    }
}
