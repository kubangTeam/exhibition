package cn.edu.hqu.cst.kubang.exhibition.service;


import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:13
 *  @Description:
 */

public interface IUserCodeService {

    /*
     * @Description 保存用户临时信息，包括邮箱、验证码、验证码发送的时间
     * @return 表受影响的行数，判断是否操作成功
     **/
    Integer saveUserCode(UserCode userCode);

    /*
     * @Description 根据邮箱查询对应的验证码和发送验证码的时间
     * @return 用户id（不是用户信息id，是UserCode的id） + 服务器发送的验证码 + 发送时间
     **/
    UserCode queryUserCodeByEmail(String email);
}
