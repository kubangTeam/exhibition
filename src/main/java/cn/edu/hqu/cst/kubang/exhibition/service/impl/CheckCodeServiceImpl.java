package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.ICheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.15 16:19
 *  @Description:
 */

@Service
public class CheckCodeServiceImpl implements ICheckCodeService {

    @Autowired
    private UserCodeDao userCodeDao;

    @Override
    public Boolean checkCode(String email, String newCode) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(newCode)) {
            System.out.println("邮箱或验证码为空！");
            return false;
        }
        //数据库根据email获取对应的code和sendingTime
        UserCode userCode = userCodeDao.queryUserCodeByEmail(email);
        Long sendingTime = Long.valueOf(userCode.getSendingTime());
        String oldCode = userCode.getCode();
        //计算时间差
        Long checkingTime = Calendar.getInstance().getTimeInMillis();
        Long minute = (checkingTime - sendingTime) / (1000 * 60);
        //30分钟内且验证码正确
        if (minute <= 30 && newCode.equals(oldCode)) {
            System.out.println("验证通过");
            System.out.println("时间差 = " + minute + " 正确的验证码 = " + oldCode + " 用户提供的验证码 = " + newCode);
            return true;
        } else {
            System.out.println("验证不通过");
            System.out.println("时间差 = " + minute + "分钟, 正确的验证码 = " + oldCode + " 用户提供的验证码 = " + newCode);
            return false;
        }
    }
}
