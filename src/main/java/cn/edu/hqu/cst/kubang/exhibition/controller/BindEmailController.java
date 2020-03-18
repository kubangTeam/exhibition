package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
@Api(tags = "邮箱服务")
public class BindEmailController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserEmailService userEmailService;

    @Autowired
    private UserInformationDao userDao;

    @ApiOperation(value = "绑定邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "电子邮件", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newCode", value = "用户输入的验证码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/check/bind")
    public Map<String, String> bindcheckCode(@RequestParam Integer userId, @RequestParam String email, @RequestParam String newCode) {
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

    @ApiOperation(value = "邮箱注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "电子邮件", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "用户密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recCode", value = "推荐码", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/check/register")
    public ModelAndView registerCheckCode(@RequestParam("email") String email
            , @RequestParam("pwd") String password, @RequestParam("code") String verifyCode
            , @RequestParam("recCode") String recCode) {
        Boolean res = userEmailService.checkCode(email, verifyCode);
        JsonBuilder json = new JsonBuilder();
        if (res) {
            //验证码检查通过 接着检查邮箱是否已被绑定
            boolean userEmailSingle = userEmailService.isUserEmailSingle(email);
            if (userEmailSingle) {
                //验证通过,用户注册成功
                userDao.UserRegisterFromEmail(email, password, recCode);
                json.add("success", "true)");
            } else {
                json.add("success", "true)");
                json.add("errCode", "301");
                json.add("errMsg", "该邮箱已被其他用户绑定！");
            }
        } else {
            json.add("success", "true)");
            json.add("errCode", "302");
            json.add("errMsg", "验证码错误！");
        }
        return json.getJsonResult();
    }

    @ApiOperation(value = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "电子邮件", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("send/forgot")
    public ModelAndView forgotSendEmail(@RequestParam("email") String email) {
        JsonBuilder json = new JsonBuilder();
        //查邮箱是否已被注册
        boolean userEmailSingle = userEmailService.isUserEmailSingle(email);
        if (userEmailSingle) {
            json.add("success", "false");
            json.add("errCode", "701");
            json.add("errMsg", "您的邮箱还没有注册!");
        } else {
            String code = UUID.randomUUID().toString().substring(0, 8);
            int status = userEmailService.sendSimpleMail(email, "酷邦助手验证码", "您好，您用于修改密码的验证码是" + code);
            if (status == 500) {
                json.add("success", "false");
                json.add("errCode", "702");
                json.add("errMsg", "发送失败，请稍后再试。");
            } else if (status == 503) {
                json.add("success", "false");
                json.add("errCode", "703");
                json.add("errMsg", "发送失败，请核对邮件是否填写正确。");
            } else {
                String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
                UserCode userCode = new UserCode(email, code, sendingTime);
                //id表示受影响的行数 常用来判断是否成功执行
                Integer id = userEmailService.saveUserCode(userCode);
                if (null != id && id > 0) {
                    json.add("success", "true");
                } else {
                    json.add("success", "false");
                    json.add("errCode", "704");
                    json.add("errMsg", "发送邮件错误！");
                }
            }
        }
        return json.getJsonResult();
    }

    @ApiOperation(value = "绑定邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "用户填写的邮箱", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/send/bind")
    public Map<String, String> bindSendEmail(@RequestParam("userId") Integer userId, @RequestParam("to") String to) {
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
        map.put("code", code);
        return map;
    }
}
