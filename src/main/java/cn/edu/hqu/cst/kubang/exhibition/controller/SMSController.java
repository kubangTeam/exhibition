package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInfoDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

//短信发送服务
@Controller
@RequestMapping("/SMS")
public class SMSController {

    @Autowired
    private UserCodeDao smsDao;

    @Autowired
    private IShortMessageService smsService;

    @Autowired
    private UserInformationDao userDao;

    @ApiOperation(value = "根据手机号码 发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/send")
    public ModelAndView sendVerifyCode(@RequestParam("phoneNumber")String phoneNumber, HttpServletRequest request){
        System.out.println("SMS send called");

        JsonBuilder json = new JsonBuilder();
        int result = smsService.sendShortMessage(phoneNumber);
        if(result ==101){
            json.add("success","false");
            json.add("errCode",String.valueOf(101));
            json.add("errMsg","服务器连接错误");
        }
        else if(result == 102){
            json.add("success","false");
            json.add("errCode",String.valueOf(102));
            json.add("errMsg","客户端错误");
        }
        //成功情况下返回的是 验证码结果
        else{
            json.add("success","true");
            String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
            UserCode userCode = new UserCode();
            userCode.setAccount(phoneNumber);
            userCode.setSendingTime(sendingTime);
            userCode.setCode(String.valueOf(result));

            smsDao.saveUserCode(userCode);
        }
        return json.getJsonResult();
    }

    @ApiOperation(value = "手机号码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "电子邮件", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "用户密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recCode", value = "推荐码", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/check/register")
    public ModelAndView registerCheckCode(@RequestParam("phoneNumber") String phoneNumber
            , @RequestParam("pwd") String password, @RequestParam("code") String verifyCode
            , @RequestParam("recCode") String recCode) {
        Boolean res = smsService.checkCode(phoneNumber, verifyCode);
        JsonBuilder json = new JsonBuilder();
        if (res) {
            //验证码检查通过 接着检查邮箱是否已被绑定
            IUserEmailService userEmailService;
            boolean userPhoneSignle = smsService.isUserPhoneSingle(phoneNumber);
            if (userPhoneSignle) {
                //验证通过,用户注册成功
                userDao.UserRegisterFromPhoneNumber(phoneNumber, password, recCode);
                json.add("success", "true)");
            } else {
                json.add("success", "true)");
                json.add("errCode", "3301");
                json.add("errMsg", "该手机号已被其他用户绑定！");
            }
        } else {
            json.add("success", "true)");
            json.add("errCode", "3302");
            json.add("errMsg", "验证码错误！");
        }
        return json.getJsonResult();
    }

}