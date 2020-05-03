package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;

import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

//短信发送服务
@Controller
@RequestMapping("/SMS")
@Api(tags = "手机注册服务")
public class SMSController {

    @Autowired
    private UserCodeDao smsDao;

    @Autowired
    IShortMessageService smsService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据手机号码 发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/send")
    @ResponseBody
    public ResponseJson<String> sendVerifyCode(@RequestParam("phoneNumber")String phoneNumber){
        System.out.println("SMS send called");

        int result = smsService.sendShortMessage(phoneNumber);
        if(result ==101){
            return new ResponseJson(false, "-008", "服务器连接错误", null);

        }
        else if(result == 102){
            return new ResponseJson(false, "-008", "客户端错误", null);
        }
        //成功情况下返回的是 验证码结果
        else{
            String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
            UserCode userCode = new UserCode();
            userCode.setAccount(phoneNumber);
            userCode.setSendingTime(sendingTime);
            userCode.setCode(String.valueOf(result));
            smsDao.saveUserCode(userCode);
            return new ResponseJson(true, "005", "验证码已发送", null);
        }
    }

    @ApiOperation(value = "手机号码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "用户密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recCode", value = "推荐码", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/check/register")
    @ResponseBody
    public ResponseJson<String> registerCheckCode(@RequestParam("phoneNumber") String phoneNumber
            , @RequestParam("password") String password, @RequestParam("verifyCode") String verifyCode
            , @RequestParam("recCode") String recCode) {
        Boolean res = smsService.checkCode(phoneNumber, verifyCode);
        JsonBuilder json = new JsonBuilder();
        if (res) {
            boolean userPhoneSingle = smsService.isUserPhoneSingle(phoneNumber);
            if (userPhoneSingle) {
                //验证通过,用户注册成功
                userService.registerByPhoneNumber(phoneNumber, password, recCode);
                return new ResponseJson(true, "005", "注册成功", null);
            } else {
                return new ResponseJson(false, "014", "该手机号已被其他用户绑定", null);
            }
        } else {

            return new ResponseJson(false, "025", "验证码错误", null);
        }
    }

}