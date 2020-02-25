package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserSMSDao {

    //根据给定的手机号查询用户，可用于检查邮箱是否被其他用户绑定
    int queryUserAccount(String account);

    //服务器发送验证码后把该验证码保存到数据库
    void saveUserCode(UserCode userCode);

    //根据要绑定的账号 查询当时发送的 验证码、发送验证码的时间
    UserCode queryUserCodeByAccount(String account);
}
