package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UIDGenerator;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author SunChonggao
 * @Date 2020/5/1 22:58
 * @Version 1.0
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int registerByPhoneNumber(String phoneNumber, String password, String recCode) {
        User user = new User();
        user.setUserAccount(phoneNumber);
        user.setUserPassword(password);
        user.setUserName(phoneNumber);
        user.setUserPermission(0);
        String userReccode = UIDGenerator.getUUID();
        user.setUserReccode(userReccode);
        user.setUserIntegral(0);
        int result = userDao.AddUserPoint(recCode);
        //可能会存在问题（多个用户推荐码相同）
        if(result ==1){
            if(userDao.UserRegisterFromPhoneNumber(user)==1)
                return 002;
        }else
            return 001;
        return  004;

    }

    public int isCompanyOrNot(int userId) {
        User user = userDao.GetUserInfoFromId(userId);
        int userCompanyId = user.getUserCompanyId();
        if(userCompanyId !=0){
            return userCompanyId;
        }else{
            return 0;
        }
    }




}
