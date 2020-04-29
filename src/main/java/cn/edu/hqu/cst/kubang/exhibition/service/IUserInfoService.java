package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;

public interface IUserInfoService {

    // 获取用户信息
    ResponseJson<User> getUserInfo(String account, String password);

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
}
