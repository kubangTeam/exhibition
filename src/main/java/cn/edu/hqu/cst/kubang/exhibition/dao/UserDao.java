package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author sunquan
 * Description 用户表mapper类
 * Date 2020.2.15
 */

@Mapper
@Repository
public interface UserDao {

    int UserRegisterFromPhoneNumber(User user);
    void UserRegisterFromEmail(String email, String password,String RecCode);
    //通过账号（电话号码）、id查询用户信息
    User GetUseInfoFromAccount(String account);
    User GetUserInfoFromId(int userId);
    //绑定用户邮箱
    int bindUserEmail(int userId, String userEmail);
    List<User> GetUserInfoFromOrganizerId(int OrganizerId);
    //检验成功以后，需要在数据库清除对应记录，以避免验证码重复使用
    int deleteUser(int userId);
    int setCompanyId(int userId,int userCompanyId);
//    void AddUserPoint(String recCode);
//    boolean CheckPassword(String account, String password);
//    void UpdatePassword(String account, String newPassword);
//    void UpdateUserInfo_UserName(String account, String userName);
//    void UpdateUserInfo_UserSex(String account, String userSex);
//    void UpdateUserInfo_UserPicture(String account,String picture);
//    int saveUserEmail(Integer userId, String userEmail);
}
