package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;

public interface IShortMessageService {
    //检查验证码
    Boolean checkCode(String phoneNumber, String newCode);

    // 收件人 主题 内容
    int sendShortMessage(String phoneNumber);

    // 保存用户临时信息，包括手机号、验证码、验证码发送的时间
    int saveUserCode(UserCode userCode);

    //根据邮箱查询对应的验证码和发送验证码的时间
    UserCode queryUserCodeByPhone(String phone);

    //该手机号是否被其他用户绑定
    boolean isUserPhoneSingle(String email);
}