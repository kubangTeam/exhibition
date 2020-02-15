package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:07
 *  @Description:
 */

@Mapper
@Repository
public interface UserCodeDao {

    //服务器发送验证码后把该验证码保存到数据库
    Integer saveUserCode(UserCode userCode);

    //根据要绑定的邮箱 查询当时发送的 验证码、发送验证码的时间
    UserCode queryUserCodeByEmail(String email);
}
