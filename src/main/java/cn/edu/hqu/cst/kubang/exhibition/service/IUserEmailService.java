package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;

/**
 * @author: 邢佳成
 * @Date: 2020.02.15 16:16
 * @Description:
 */
public interface IUserEmailService {
    //绑定邮箱到指定userId的用户
    int bindUserEmail(Integer userId, String userEmail);

    //检查验证码
    Boolean checkCode(String email, String newCode);

    //该邮箱是否被其他用户绑定
    boolean isUserEmailSingle(String email);

    //该用户是否已经绑定了邮箱
    boolean isUserEmailBound(Integer userId);

    // 收件人 主题 内容
    int sendSimpleMail(String to, String subject, String content);

    // 保存用户临时信息，包括邮箱、验证码、验证码发送的时间
    Integer saveUserCode(UserCode userCode);

    //根据邮箱查询对应的验证码和发送验证码的时间
    UserCode queryUserCodeByEmail(String email);
}
