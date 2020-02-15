package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.ICheckEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.ISendMailService;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.UUID;

/**
 * @author: 邢佳成
 * @Date: 2020.02.15 16:21
 * @Description:
 */

@RestController
@RequestMapping("/email")
public class SendMailController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ISendMailService mailService;

    @Autowired
    private IUserCodeService userCodeService;

    @Autowired
    private ICheckEmailService checkEmailService;

    @PostMapping("/send")
    public String sendEmail(@RequestParam("userId") Integer userId,@RequestParam("to") String to) {
        //首先检查该用户是否已经绑定了邮箱
        boolean isUserEmailBound = checkEmailService.isUserEmailBound(userId);
        if (isUserEmailBound){
            return "您已绑定邮箱";
        }
        //定义发送内容
        String subject = "酷邦助手验证码";
        String code = UUID.randomUUID().toString().substring(0, 8);
        String content = "你好，您的验证码是: " + code;
        //调用发送方法
        int status = mailService.sendSimpleMail(to, subject, content);
        if (status == 500) {
            return "发送失败，请稍后再试。";
        }
        if (status == 503) {
            return "发送失败，请核对邮件是否填写正确。";
        } else {
            System.out.println("邮件已发送,验证码是" + code);
            //保存数据库
            String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
            UserCode userCode = new UserCode(to, code, sendingTime);
            //id表示受影响的行数 常用来判断是否成功执行
            Integer id = userCodeService.saveUserCode(userCode);
            if (null != id && id > 0) {
                System.out.println("插入成功！" + userCode.toString());
                return "已发送验证";
            } else {
                logger.error("发送邮件错误");
                return "出现错误";
            }
        }
    }
}
