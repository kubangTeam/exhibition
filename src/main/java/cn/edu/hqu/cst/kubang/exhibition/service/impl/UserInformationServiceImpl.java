package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl implements IUserInformationService {

    @Autowired
    UserInformation userInformation;

    @Autowired
    UserInformationDao userInformationDao;
    @Override
    public int registerByPhoneNumber() {
        return 0;
    }

    /**
     * 判断该账号是否认证为商家
     */
    @Override
    public int isCompanyOrNot(int userId) {
        userInformation = userInformationDao.GetUserInfoFromId(userId);
        int userCompanyId = userInformation.getUserCompanyId();
        if(userCompanyId !=0){
            return userCompanyId;
        }else{
            return 0;
        }
    }
}
