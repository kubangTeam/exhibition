package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserEmailDao;
import cn.edu.hqu.cst.kubang.exhibition.service.IBindUserEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:20
 *  @Description:
 */
@Service
public class BindUserEmailServiceImpl implements IBindUserEmailService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserEmailDao userEmailDao;

    @Override
    public int bindUserEmail(Integer userId, String userEmail) {
        if(StringUtils.isEmpty(userId) && StringUtils.isEmpty(userEmail)){
            logger.error("用户主键或用户邮箱为空或为null");
            return -1;
        }
        int res = userEmailDao.saveUserEmail(userId, userEmail);
        //额外的逻辑操作...
        return res;
    }
}
