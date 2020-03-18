package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import io.swagger.annotations.Api;
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
@Api(tags = "短信发送服务")
public class SMSController {

    @Autowired
    private UserCodeDao smsDao;

    @Autowired
    private IShortMessageService smsService;

    //接口 send，发送短信 验证码 到手机
    //请求参数1：phoneNumber 手机号码
    @ApiOperation(value = "发送短信 验证码 到手机")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/send")
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
            UserCode userCode = new UserCode(phoneNumber,String.valueOf(result),sendingTime);
            smsDao.saveUserCode(userCode);
        }
        return json.getJsonResult();
    }
}
