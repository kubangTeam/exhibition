package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor//无参构造
@Accessors(chain = true)
@Component
public class UserCode implements Serializable {
    private Integer id; //数据库主键
    private String account;//电话号码
    private String code;//验证码
    private String sendingTime;//发送邮件的时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    @Override
    public String toString() {
        return "UserCode{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", code='" + code + '\'' +
                ", sendingTime='" + sendingTime + '\'' +
                '}';
    }
}
