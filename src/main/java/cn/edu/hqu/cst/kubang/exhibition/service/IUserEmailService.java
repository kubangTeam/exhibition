package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 * @author: 邢佳成
 * @Date: 2020.02.15 16:16
 * @Description:
 */
public interface IUserEmailService {

    //检查验证码
    Boolean checkCode(String email, String newCode);

    //该邮箱是否被其他用户绑定
    boolean isUserEmailSingle(String email);

    //该用户是否已经绑定了邮箱
    boolean isUserEmailBound(Integer userId);

    // 收件人 主题 内容
    int sendSimpleMail(String to, String subject, String content);
}
