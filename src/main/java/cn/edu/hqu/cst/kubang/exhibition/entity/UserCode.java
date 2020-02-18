package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data// setter/getter、equals、canEqual、hashCode、toString
@NoArgsConstructor//无参构造
public class UserCode implements Serializable {
    private Integer id; //数据库主键
    private String email;//用户邮箱
    private String code;//验证码
    private String sendingTime;//发送邮件的时间

    public UserCode(String email, String code, String sendingTime) {
        this.email = email;
        this.code = code;
        this.sendingTime = sendingTime;
    }
}
