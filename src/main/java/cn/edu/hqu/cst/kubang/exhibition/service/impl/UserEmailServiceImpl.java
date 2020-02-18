package cn.edu.hqu.cst.kubang.exhibition.service.impl;


import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserEmailDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
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
    @Autowired
    private UserEmailDao userEmailDao;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;

    @Override
    @NullDisable
    public int bindUserEmail(Integer userId, String userEmail) {
        return userEmailDao.saveUserEmail(userId, userEmail);
    }

    @Override
    @NullDisable
    public Boolean checkCode(String email, String newCode) {
        //数据库根据email获取对应的code和sendingTime
        UserCode userCode = userEmailDao.queryUserCodeByEmail(email);
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
    @NullDisable
    public boolean isUserEmailSingle(String email) {
        return userEmailDao.queryUserByEmail(email) == 0 ? true : false;
    }

    @Override
    @NullDisable
    public boolean isUserEmailBound(Integer userId) {
        UserInformation user = userEmailDao.queryUserById(userId);
        return !StringUtils.isEmpty(user.getUserEmail());
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
            e.printStackTrace();
            return 500; //很多错误 不一一列举 除了邮件地址不正确导致发送失败 其余异常统一500
        }
        return 200;
    }

    @Override
    @NullDisable
    public Integer saveUserCode(UserCode userCode) {
        return userEmailDao.saveUserCode(userCode);
    }

    @Override
    @NullDisable
    public UserCode queryUserCodeByEmail(String email) {
        return userEmailDao.queryUserCodeByEmail(email);
    }
}
