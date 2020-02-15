package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:17
 *  @Description:
 */
@Service
public class UserCodeServiceImpl implements IUserCodeService {

    @Autowired
    private UserCodeDao userCodeDao;

    @Override
    public Integer saveUserCode(UserCode userCode) {
        if (StringUtils.isEmpty(userCode))
            return -1;
        Integer res = userCodeDao.saveUserCode(userCode);
        //额外的逻辑操作...
        return res;
    }

    @Override
    public UserCode queryUserCodeByEmail(String email) {
        if (StringUtils.isEmpty(email))
            return null;
        UserCode userCode = userCodeDao.queryUserCodeByEmail(email);
        //额外的逻辑操作...
        return userCode;
    }
}
