package cn.edu.hqu.cst.kubang.exhibition.service;

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
        user.setUserReccode(recCode);
        return userDao.UserRegisterFromPhoneNumber(user);
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
