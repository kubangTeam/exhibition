package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserEmailDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.ICheckEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:19
 *  @Description:
 */

@Service
public class CheckEmailServiceImpl implements ICheckEmailService {

    @Autowired
    private UserEmailDao userEmailDao;

    @Override
    public boolean isUserEmailSingle(String email) {
        if (StringUtils.isEmpty(email)){
            return false;
        }
        int res = userEmailDao.queryUserByEmail(email);
        if (res == 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isUserEmailBound(Integer userId) {
        if (StringUtils.isEmpty(userId)){
            return false;
        }
        UserInformation user = userEmailDao.queryUserById(userId);
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getUserEmail())){
            return false;
        }
        else {
            return true;
        }
    }
}
