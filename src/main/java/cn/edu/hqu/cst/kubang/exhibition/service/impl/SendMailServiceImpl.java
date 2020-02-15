package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.service.ISendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author: 邢佳成
 * @Date: 2020.02.13 13:12
 * @Description: 发送邮件接口实现
 */

@Service
public class SendMailServiceImpl implements ISendMailService {

    @Autowired
    private JavaMailSender mailSender;

    //yml中我的qq邮箱
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public int sendSimpleMail(String to, String subject, String content) throws MailSendException{
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
            return 503; //Invalid Addresses 邮件地址不正确
        }catch (Exception e){
            return 500; //很多错误 不一一列举 除了邮件地址不正确导致发送失败 其余异常统一500
        }
        return 200;
    }
}
