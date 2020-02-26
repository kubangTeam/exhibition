package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserCodeDao {
    //服务器发送验证码后把该验证码保存到数据库
    Integer saveUserCode(UserCode userCode);

    //根据要绑定的账号 查询当时发送的 验证码、发送验证码的时间
    UserCode queryUserCode(String account);

    //检验成功以后，需要在数据库清除对应记录，以避免验证码重复使用
    void deleteUserCode(String account);
}
