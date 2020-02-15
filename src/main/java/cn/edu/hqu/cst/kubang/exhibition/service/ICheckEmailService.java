package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:15
 *  @Description:
 */

public interface ICheckEmailService {

    //该邮箱是否被其他用户绑定
    boolean isUserEmailSingle(String email);

    //该用户是否已经绑定了邮箱
    boolean isUserEmailBound(Integer userId);
}
