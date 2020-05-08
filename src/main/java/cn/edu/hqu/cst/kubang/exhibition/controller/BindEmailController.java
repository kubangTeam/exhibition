package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
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
 * @Description: /email
 * 1、/check/bind  绑定邮箱
 * 2、/check/register 邮箱注册
 * 3、/send/forgot 忘记密码
 */
@RestController
@RequestMapping("/email")
@Api(tags = "邮箱服务")
public class BindEmailController {
    @Autowired
    private IUserEmailService userEmailService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCodeDao userCodeDao;
    @Autowired
    private AccountServiceImp accountServiceImp;

    /**
     * 根据用户的id和邮箱发送验证码
     *
     * @param userId
     * @param to
     * @return
     */
    @ApiOperation(value = "发送验证码", notes = "根据用户的id和邮箱发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "用户填写的邮箱", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/send/bind")
    public ResponseJson<String> bindSendEmail(@RequestParam("userId") Integer userId, @RequestParam("to") String to) {
        //首先检查该用户是否已经绑定了邮箱
        boolean isUserEmailBound = userEmailService.isUserEmailBound(userId);
        if (isUserEmailBound) {
            ResponseJson<String> stringResponseJson = new ResponseJson<>(false, null, "您已绑定邮箱", null);
            return stringResponseJson;
        }

        //定义发送内容
        String subject = "酷邦助手验证码";
        String code = UUID.randomUUID().toString().substring(0, 8);
        String content = "你好，您的验证码是: " + code;
        //调用发送方法
        int status = userEmailService.sendSimpleMail(to, subject, content);
        if (status == 200) {
            UserCode userCode = new UserCode(null, to, code, String.valueOf(Calendar.getInstance().getTimeInMillis()));
            Integer changeRow = userCodeDao.saveUserCode(userCode);
            if (changeRow == 1) {
                ResponseJson<String> stringResponseJson = new ResponseJson<>(true, "005", "已发送", null);
                return stringResponseJson;
            } else {
                ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "系统错误", null);
                return stringResponseJson;
            }
        }
        ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "系统错误", null);
        return stringResponseJson;
    }

    @ApiOperation(value = "发送验证码", notes = "给邮箱发送验证码（用于邮箱注册）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "to", value = "用户填写的邮箱", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/send/emailRegister")
    public ResponseJson<String> bindSendEmail(@RequestParam("to") String to) {
        //定义发送内容
        String subject = "酷邦助手验证码";
        String code = UUID.randomUUID().toString().substring(0, 8);
        String content = "你好，您的验证码是: " + code;

        //删除数据库中存在的相同邮箱的记录
        userCodeDao.deleteUserCode(to);
        //调用发送方法
        int status = userEmailService.sendSimpleMail(to, subject, content);
        if (status == 200) {
            UserCode userCode = new UserCode(null, to, code, String.valueOf(Calendar.getInstance().getTimeInMillis()));
            Integer changeRow = userCodeDao.saveUserCode(userCode);
            if (changeRow == 1) {
                ResponseJson<String> stringResponseJson = new ResponseJson<>(true, "005", "已发送", null);
                return stringResponseJson;
            } else {
                ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "系统错误", null);
                return stringResponseJson;
            }
        }
        ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "系统错误", null);
        return stringResponseJson;
    }




    /**
     * 根据用户的id、邮箱、发送验证码绑定用户账号
     */
    @ApiOperation(value = "绑定邮箱", notes = "根据用户的id、邮箱、发送验证码绑定用户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "电子邮件", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newCode", value = "用户输入的验证码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/check/bind")
    public ResponseJson<String> bindCheckCode(Integer userId, String email, String newCode) {
        Boolean res = userEmailService.checkCode(email, newCode);
        if (res) {
            //验证通过，删除usecode数据避免验证码重复使用
            userCodeDao.deleteUserCode(email);
            //验证码检查通过 接着检查邮箱是否已被绑定
            boolean userEmailSingle = userEmailService.isUserEmailSingle(email);
            if (userEmailSingle) {
                //验证通过  将该邮箱存进数据库 与该用户绑定
                int status = userDao.bindUserEmail(userId, email);
                if (status == 1) {
                    ResponseJson<String> stringResponseJson = new ResponseJson<>(true, "005", "绑定成功", null);
                    return stringResponseJson;
                } else {
                    ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "绑定失败", null);
                    return stringResponseJson;
                }
            } else {
                ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "该邮箱已被其他用户绑定", null);
                return stringResponseJson;
            }
        } else {
            ResponseJson<String> stringResponseJson = new ResponseJson<>(false, "-001", "验证码错误", null);
            return stringResponseJson;
        }
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
                                        , @RequestParam("pwd") String password,
                                          @RequestParam("code") String verifyCode
                                        , @RequestParam("recCode") String recCode) {
        Boolean res = userEmailService.checkCode(email, verifyCode);
        JsonBuilder json = new JsonBuilder();
        if (res) {
            //验证码检查通过 接着检查邮箱是否已被绑定
            boolean userEmailSingle = userEmailService.isUserAccountSingle(email);
            if (userEmailSingle) {
                //验证通过,用户注册成功
                int i = accountServiceImp.registerFromEmail(email, password, recCode);
                if(i==002)
                    json.add("success", "true)");
                else if(i==001) {
                    json.add("success", "true)");
                    json.add("errCode", "301");
                    json.add("errMsg", "推荐码填写错误");
                }
            } else {
                json.add("success", "true)");
                json.add("errCode", "301");
                json.add("errMsg", "该邮箱已被其他用户注册！");
            }
        } else {
            json.add("success", "true)");
            json.add("errCode", "302");
            json.add("errMsg", "验证码错误！");
        }
        return json.getJsonResult();
    }


    /**
     * 根据用户的id、邮箱、发送验证码帮用户找回密码
     */
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
                UserCode userCode = new UserCode();
                userCode.setSendingTime(sendingTime);
                userCode.setCode(code);
                userCode.setAccount(email);
                //id表示受影响的行数 常用来判断是否成功执行
                Integer id = userCodeDao.saveUserCode(userCode);
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

}
