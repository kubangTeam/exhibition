package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * author sunquan
 * Description 用户表mapper类
 * Date 2020.2.15
 */

@Mapper
@Repository
public interface UserInformationDao {
    void UserRegisterFromPhoneNumber(String phoneNumber, String newUserName,String password,String RecCode);
    void UserRegisterFromEmail(String email, String password,String RecCode);
    UserInformation GetUseInfoFromAccount(String account);
    UserInformation GetUserInfoFromId(int userId);
    void AddUserPoint(String recCode);
    boolean CheckPassword(String account, String password);
    void UpdatePassword(String account, String newPassword);
    void UpdateUserInfo(String account, String userName, String userSex, String picture);
}
