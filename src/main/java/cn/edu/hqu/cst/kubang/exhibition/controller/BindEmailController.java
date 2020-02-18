package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserEmailService userEmailService;

    //前端必须是表单数据,如果是其他比如json格式则会报错
    @PostMapping("/check")
    public Map<String, String> checkCode(@RequestParam("userId") Integer userId, @RequestParam("email") String email, @RequestParam("newCode") String newCode) {
        System.out.println("checkCode: ");
        System.out.println("userId: " + userId);
        System.out.println("email: " + email);
        System.out.println("newCode: " + newCode);
        Boolean res = userEmailService.checkCode(email, newCode);
        String value;
        if (res) {
            //验证码检查通过 接着检查邮箱是否已被绑定
            boolean userEmailSingle = userEmailService.isUserEmailSingle(email);
            if (userEmailSingle) {
                //验证通过  将该邮箱存进数据库 与该用户绑定
                int status = userEmailService.bindUserEmail(userId, email);
                if (status > 0) {
                    value = "验证通过！";
                } else {
                    logger.error("绑定用户邮箱出现错误");
                    value = "绑定用户邮箱出现错误";
                }
            } else {
                value = "该邮箱已被其他用户绑定！";
            }
        } else {
            value = "验证码错误！";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        return map;
    }

    @PostMapping("/send")
    public Map<String, String> sendEmail(@RequestParam("userId") Integer userId, @RequestParam("to") String to) {
        //首先检查该用户是否已经绑定了邮箱
        boolean isUserEmailBound = userEmailService.isUserEmailBound(userId);
        String value;
        String code = "";
        System.out.println(isUserEmailBound);
        if (isUserEmailBound) {
            value = "您已绑定邮箱";
        } else {
            //定义发送内容
            String subject = "酷邦助手验证码";
            code = UUID.randomUUID().toString().substring(0, 8);
            String content = "你好，您的验证码是: " + code;
            //调用发送方法
            int status = userEmailService.sendSimpleMail(to, subject, content);
            if (status == 500) {
                value = "发送失败，请稍后再试。";
            } else if (status == 503) {
                value = "发送失败，请核对邮件是否填写正确。";
            } else {
                System.out.println("邮件已发送,验证码是" + code);
                //保存数据库
                String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
                UserCode userCode = new UserCode(to, code, sendingTime);
                //id表示受影响的行数 常用来判断是否成功执行
                Integer id = userEmailService.saveUserCode(userCode);
                if (null != id && id > 0) {
                    System.out.println("插入成功！" + userCode.toString());
                    value = "已发送验证";
                } else {
                    logger.error("发送邮件错误");
                    value = "出现错误";
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code",code);
        return map;
    }
}
