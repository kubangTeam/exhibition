package cn.edu.hqu.cst.kubang.exhibition.service.impl;


import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInfoDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.List;

@Service
public class UserEmailServiceImpl implements IUserEmailService {

    @Autowired
    private UserCodeDao userCodeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;



    @Override
    @NullDisable
    /**
     * 验证发送的验证码是否有效
     * 验证码时效为30分钟
     */
    public Boolean checkCode(String email, String newCode) {
        //数据库根据email获取对应的code和sendingTime
        if(userCodeDao.queryUserCodeByAccount(email)!=null){
            UserCode userCode = userCodeDao.queryUserCodeByAccount(email);
            Long sendingTime = Long.valueOf(userCode.getSendingTime());
            String oldCode = userCode.getCode();
            //计算时间差
            Long checkingTime = Calendar.getInstance().getTimeInMillis();
            Long minute = (checkingTime - sendingTime) / (1000 * 60);
            //30分钟内且验证码正确
            return minute <= 30 && newCode.equals(oldCode);
        }else
            return false;

    }

    @Override
    public boolean isUserEmailSingle(String email) {
        List<User> users = userInfoDao.queryUserInfoByEmail(email);
        return  users.size() == 0;
    }

    @Override
    public boolean isUserAccountSingle(String account) {
        User user = userInfoDao.queryUserInfoByAccount(account);
        return  user ==null;
    }


    @Override
    @NullDisable
    public boolean isUserEmailBound(Integer userId) {
        User user = userDao.GetUserInfoFromId(userId);
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
            throw mailSendException;
        } catch (Exception e) {
            throw e;
        }
        return 200;
    }

}
