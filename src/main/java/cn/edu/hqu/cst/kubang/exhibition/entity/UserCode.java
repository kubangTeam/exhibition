package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data// setter/getter、equals、canEqual、hashCode、toString
@NoArgsConstructor//无参构造
public class UserCode implements Serializable {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return account;
    }

    public void setEmail(String email) {
        this.account = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    private Integer id; //数据库主键
    private String account;
    private String code;//验证码
    private String sendingTime;//发送邮件的时间

    public UserCode(String account, String code, String sendingTime) {
        this.account = account;
        this.code = code;
        this.sendingTime = sendingTime;
    }
}
