package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 * @Author: 邢佳成
 * @Description: 检查用户提交的验证码
 * @Date: Create in 2020/02/13 13:16
 */
public interface ICheckCodeService {

    /**
     * 检查验证码
     * @param email 注册的邮箱
     * @param newCode 用户提交的验证码
     */
    Boolean checkCode(String email, String newCode);
}
