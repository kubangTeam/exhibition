package cn.edu.hqu.cst.kubang.exhibition.service.impl;


import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserEmailDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;

@Service
public class UserEmailServiceImpl implements IUserEmailService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserEmailDao userEmailDao;
    @Autowired
    private UserCodeDao userCodeDao;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;

    @Override
    public int bindUserEmail(Integer userId, String userEmail) {
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(userEmail)) {
            logger.error("用户主键或用户邮箱为空或为null");
            return -1;
        }
        int res = userEmailDao.saveUserEmail(userId, userEmail);
        //额外的逻辑操作...
        return res;
    }

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

    @Override
    public boolean isUserEmailSingle(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        int res = userEmailDao.queryUserByEmail(email);
        if (res == 0) {
            //额外逻辑操作
            return true;
        } else {
            //额外逻辑操作
            return false;
        }
    }

    @Override
    public boolean isUserEmailBound(Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        UserInformation user = userEmailDao.queryUserById(userId);
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getUserEmail())) {
            //额外逻辑操作
            return false;
        } else {
            //额外逻辑操作
            return true;
        }
    }

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public int sendSimpleMail(String to, String subject, String content) throws MailSendException {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        try {
            mailSender.send(message);
        } catch (MailSendException mailSendException) {
            //额外逻辑操作
            return 503; //Invalid Addresses 邮件地址不正确
        } catch (Exception e) {
            return 500; //很多错误 不一一列举 除了邮件地址不正确导致发送失败 其余异常统一500
        }
        return 200;
    }

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
