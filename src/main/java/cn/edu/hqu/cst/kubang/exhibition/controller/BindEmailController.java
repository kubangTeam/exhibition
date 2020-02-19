package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: 邢佳成
 * @Date: 2020.02.17 20:09
 * @Description:
 */
@RestController
@RequestMapping("/email")
public class BindEmailController {

    @Autowired
    private IUserEmailService userEmailService;

    @PostMapping("/check")
    public Map<String, String> checkCode(@RequestParam Integer userId, @RequestParam String email, @RequestParam String newCode) {
        System.out.println("checkCode: userId=" + userId + ",email=" + email + ",验证码=" + newCode);
        String value;
        String code;
        //检查验证码
        if (userEmailService.checkCode(email, newCode)) {
            //检查邮箱是否已被绑定
            if (userEmailService.isUserEmailSingle(email)) {
                //验证通过  将该邮箱存进数据库 与该用户绑定
                if (userEmailService.bindUserEmail(userId, email) > 0) {
                    value = "验证通过！";
                    code = "005";
                } else {
                    value = "绑定用户邮箱出现错误";
                    code = "-004";
                }
            } else {
                value = "该邮箱已被其他用户绑定！";
                code = "015";
            }
        } else {
            value = "验证码错误！";
            code = "025";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    @PostMapping("/send")
    public Map<String, String> sendEmail(@RequestParam("userId") Integer userId, @RequestParam("to") String to) {
        //首先检查该用户是否已经绑定了邮箱
        boolean isUserEmailBound = userEmailService.isUserEmailBound(userId);
        String value = "";
        String code = "";
        if (isUserEmailBound) {
            value = "您已绑定邮箱";
            code = "015";
        } else {
            //定义发送内容
            String subject = "酷邦助手验证码";
            String verificationCode = UUID.randomUUID().toString().substring(0, 8);
            String content = "你好，您的验证码是: " + verificationCode;
            if (userEmailService.sendSimpleMail(to, subject, content) == 200) {
                System.out.println("邮件已发送,验证码是: " + verificationCode);
                String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
                UserCode userCode = new UserCode(to, content, sendingTime);
                Integer id = userEmailService.saveUserCode(userCode);
                if (null != id && id > 0) {
                    value = "已发送验证";
                    code = "019";
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
}
