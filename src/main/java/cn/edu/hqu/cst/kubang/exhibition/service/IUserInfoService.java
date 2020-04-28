package cn.edu.hqu.cst.kubang.exhibition.service;

public interface IUserInfoService {

    // 获取用户信息
    Object getUserInfo(String account,String password);

    // 改头像
    Object changePhoto(Integer id, String photo);

    // 改名字
    Object changeName(Integer id, String name);

    // 改邮箱
    Object changeEmail(Integer id, String email);

    // 改密码
    Object changePass(Integer id, String newPassword, String oldPassword);
}
