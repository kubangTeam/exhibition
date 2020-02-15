package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:16
 *  @Description:
 */

public interface IBindUserEmailService {

    //绑定邮箱到指定userId的用户
    int bindUserEmail(Integer userId, String userEmail);
}
