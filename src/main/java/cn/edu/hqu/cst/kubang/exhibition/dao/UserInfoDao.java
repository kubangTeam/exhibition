package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserIntegral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoDao {

    //查看用户信息
    User queryUserInfoById(Integer userId);

    User queryUserInfoByAccount(String userAccount);

    List<User> queryUserInfoByEmail(String email);

    List<User> queryUserInfoByCompany(Integer userCompanyId);

    User queryUserInfoByReccode(String userReccode);

    //修改用户信息
    Integer changeUserName(@Param("id") Integer id, @Param("name") String name);

    Integer changeUserEmail(@Param("id") Integer id,@Param("email") String email);

    Integer changeUserPass(@Param("id") Integer id,@Param("password") String password);

    Integer changeUserPhoto(@Param("userId") Integer userId,@Param("userPicture") String userPicture);

    // 查询用户积分历史
    List<UserIntegral> queryUserIntegral(Integer id);
}
