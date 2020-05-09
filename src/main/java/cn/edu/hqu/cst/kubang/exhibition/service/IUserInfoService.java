package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface IUserInfoService {

    // 获取用户信息
    ResponseJson<User> getUserInfo(String account, String password);

    //检查账户是否存在
    boolean isAccountExist(String account);

    // 改头像
    ResponseJson changePhoto(Integer id, String photo);

    // 改名字
    ResponseJson changeName(Integer id, String name);

    // 改邮箱
    ResponseJson changeEmail(Integer id, String email);

    // 改密码
    ResponseJson changePass(Integer id, String newPassword, String oldPassword);

    // 查询积分历史
    ResponseJson queryUserIntegral(Integer id);

    // 重置密码
    ResponseJson resetPassword(String userAccount, String code, String newPassword);

    // 微信登录
    ResponseJson wxLoginIn(String code);
}
