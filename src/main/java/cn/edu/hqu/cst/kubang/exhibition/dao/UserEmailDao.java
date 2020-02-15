package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:10
 *  @Description:
 */

@Mapper
@Repository
public interface UserEmailDao {

    //根据给定的邮箱查询用户，可用于检查邮箱是否被其他用户绑定
    int queryUserByEmail(String email);

    //当验证码检查通过时，根据用户id找到该用户且绑定邮箱
    int saveUserEmail(@Param("userId") Integer userId, @Param("userEmail") String userEmail);

    //查根据userId询用户
    UserInformation queryUserById(Integer userId);
}
