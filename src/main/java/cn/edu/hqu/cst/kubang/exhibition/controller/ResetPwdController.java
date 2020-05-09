package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserInfoService;
import cn.edu.hqu.cst.kubang.exhibition.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.UUID;

/**
 * @Author SunChonggao
 * @Date 2020/5/8 19:41
 * @Version 1.0
 * @Description:
 */
@RestController
@RequestMapping("/resetPwd")
@Api(tags = "忘记密码")
public class ResetPwdController {
    private IUserInfoService userInfoService;
    private BindEmailController bindEmailController;
    private SMSController smsController;
    private UserService userService;

    @Autowired
    public ResetPwdController(IUserInfoService userInfoService,
                              BindEmailController bindEmailController,
                              SMSController smsController,
                              UserService userService ) {
        this.userInfoService = userInfoService;
        this.bindEmailController = bindEmailController;
        this.smsController = smsController;
        this.userService = userService;
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.GET)
    @ApiOperation(value = "发送重置密码的验证码", notes = "用户名为空(010),用户名不存在(011),")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount", value = "手机号或邮箱", required = true, dataType = "String", paramType = "query")
    })
    public ResponseJson<String> sendCode (String userAccount) {
        if (userAccount == null || userAccount.isEmpty())
            return new ResponseJson(false, "010", "用户名为空", null);
        else if (!userInfoService.isAccountExist(userAccount))
            return new ResponseJson(false, "011", "用户名不存在", null);
        else {
            //删除旧验证码
            userService.deleteUserCode(userAccount);
            //用户名为邮箱
            if (userAccount.contains(".") == true) {
                //定义发送内容
                String subject = "酷邦助手重置密码";
                String code = UUID.randomUUID().toString().substring(0, 8);
                String content = "你好，您的验证码是: " + code;
                return bindEmailController.sendMail(userAccount, subject, content, code);
            }
            //用户名为手机
            else
                return smsController.sendVerifyCode(userAccount);

        }
    }
    @ApiOperation(value = "重置密码密码验证", notes = "返回005（成功）,024（参数不合法）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseJson resetPassword(String userAccount, String code, String newPassword) {
        return userInfoService.resetPassword(userAccount, code, newPassword);
    }



}

