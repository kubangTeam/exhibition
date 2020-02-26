package cn.edu.hqu.cst.kubang.exhibition.controller;


import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserSessionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;

import cn.edu.hqu.cst.kubang.exhibition.service.IUserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

@Controller
@RequestMapping("/User")
//提供用户注册，登录，获取用户信息等服务有关的控制器
public class UserController {

    @Autowired
    private UserInformationDao userDao;

    @Autowired
    private UserSessionDao sessionDao;

    //接口 register，注册
    //请求参数1: account 账户（手机号码/邮箱）
    //请求参数2: pwd 密码
    //请求参数3: verifyCode 验证码
    //请求参数4: recCode 推荐码

    @RequestMapping(value = "/register")
    public ModelAndView Register(@RequestParam("account") String account, @RequestParam("pwd") String password,
                                 @RequestParam("code") String code, @RequestParam("recCode") String recCode,
                                 HttpServletRequest request, HttpSession session)
    {
        System.out.println("User Register called");

        JsonBuilder json= new JsonBuilder();
        UserInformation user = userDao.GetUseInfoFromAccount(account);
        //用户已经存在
        if(user!=null){
            json.add("success","false");
            json.add("errMsg","用户已经存在!");
            return json.getJsonResult();
        }
        userDao.UserRegisterFromPhoneNumber(account,password,recCode);

        json.add("success","true");
        return json.getJsonResult();
    }

    //接口 getUserInfo
    @RequestMapping(value = "/getUserInfo")
    public ModelAndView getUserInfo(HttpServletRequest request){
        System.out.println("getUserInfo called");

        String sessionId = request.getRequestedSessionId();
        int userId = sessionDao.queryBySessionId(sessionId);
        UserInformation user = userDao.GetUserInfoFromId(Integer.valueOf(userId));

        JsonBuilder json= new JsonBuilder();
        json.add("userCity",user.getUserChooseCity());
        json.add("userName",user.getUserName());
        json.add("userSex",user.getUserSex());
        json.add("userRecCode",user.getUserReccode());
        json.add("userIntegral", String.valueOf(user.getUserIntegral()));
        json.add("userPicture",user.getUserPicture());

        return json.getJsonResult();
    }

    //接口 login，登录
    //请求参数1: account 账户（手机号码/邮箱）
    //请求参数2: pwd 密码
    @RequestMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    ModelAndView Login(@RequestParam("account") String account,@RequestParam("password") String password,
                       HttpServletRequest request, HttpSession session) {
        System.out.println("User login called");

        JsonBuilder json= new JsonBuilder();
        UserInformation user=  userDao.GetUseInfoFromAccount(account);

        //账号存在
        if(user!=null){
            //判断找到的账号密码是否和输入的密码一致
            //密码正确
            if(user.getUserPassword().equals((password))){

                String sessionID = request.getRequestedSessionId();
                int userId = user.getUserId();

                //单点登录的实现原理
                //这里SessionManageService 保存了SessionID
                //在用户登录的时候，根据账户名获取是否已经存储了sessionId，如果存在，则删除原来的那个sessionId，
                //然后重新保存当前的sessionId；如果不存在，则直接保存当前的sessionId；
                //同时将当前的账户信息保存到全局的Session里面；
                if (sessionDao.queryByUserID(userId)==null) {
                    sessionDao.add(userId, sessionID);
                }
                //如果同一个会话ID 再次申请登录
                else if(sessionDao.containsSessionId(sessionID)>0)
                {
                    //登陆的账号不一样，则替换
                    if(!sessionID.equals(sessionDao.queryByUserID(userId)))
                    {
                        sessionDao.removeByUserId(userId);
                        sessionDao.add(userId, sessionID);
                    }
                    //否则提示，您已登录
                    else{
                        json.add("success","false");
                        json.add("errCode","001");
                        json.add("errMsg","已经在线了!");
                        return json.getJsonResult();
                    }
                }
                //这里添加的是 前端 需要的东西，密码什么的不需要
                json.add("success","true");
                json.addRoot("userInfo");
                json.addChild("userInfo","userAccount",user.getUserAccount());
                json.addChild("userInfo","userCity",user.getUserChooseCity());
                json.addChild("userInfo","userName",user.getUserName());
                json.addChild("userInfo","userSex",user.getUserSex());
                json.addChild("userInfo","userRecCode",user.getUserReccode());
                json.addChild("userInfo","userIntegral", String.valueOf(user.getUserIntegral()));
                json.addChild("userInfo","userPicture",user.getUserPicture());

                session.setAttribute("userMsg", user);
            }
            //密码错误
            else{
                json.add("success","false");
                json.add("errCode","002");
                json.add("errMsg","密码错误!");
            }
        }
        //账号不存在
        else{
            json.add("success","false");
            json.add("errCode","003");
            json.add("errMsg","账号不存在!");
        }
        return json.getJsonResult();
    }

    //接口 CancelLogin，注销
    //请求参数1：phoneNumber 手机号码
    @RequestMapping(value = "/cancelLogin",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    void  CancelLogin(HttpSession session, HttpServletRequest request){
        System.out.println("CancelLogin called");

        JsonBuilder json= new JsonBuilder();
        //让seesion失效，并且移除记录，则实现注销
        session.invalidate();
        sessionDao.removeByUserId(sessionDao.queryBySessionId(request.getRequestedSessionId()));
    }

    //接口 UpdateUserInfo,修改用户信息
    //请求参数1:userName 昵称
    //请求参数2:userSex 性别
    @RequestMapping(value = "/updateUserInfo",produces =  "application/json;charset=UTF-8")
    public @ResponseBody
    void  UpdateUserInfo(HttpServletRequest request, HttpSession session){
        System.out.println("UpdateUserInfo called!");
        String userName = request.getParameter("userName");
        String userSex = request.getParameter("userSex");
        String userPicture = request.getParameter("userPicture");
        //这时候根据sessionID，取得用户Id，从而获取用户信息
        String sessionId = request.getRequestedSessionId();
        int userId = sessionDao.queryBySessionId(sessionId);
        UserInformation user = userDao.GetUserInfoFromId(Integer.valueOf(userId));
        userDao.UpdateUserInfo(user.getUserAccount(),userName,userSex,userPicture);
    }

    //接口 ModifyPassword,修改密码
    //请求参数1:oldpwd 原密码
    //请求参数2++:newpdw 新密码
    @RequestMapping(value = "/modifyPassword",produces =  "application/json;charset=UTF-8")
    public @ResponseBody
    ModelAndView ModifyPassword(HttpServletRequest request, HttpSession session){
        String oldPwd = request.getParameter("oldpwd");
        String newPwd = request.getParameter("newpwd");

        JsonBuilder json = new JsonBuilder();

        //首先要检查用户的原密码是否正确
        //这时候根据sessionID，取得用户Id，从而获取用户信息

        String sessionId = request.getRequestedSessionId();
        int userId = sessionDao.queryBySessionId(sessionId);
        UserInformation user = userDao.GetUserInfoFromId(Integer.valueOf(userId));

        if(user.getUserPassword().equals(oldPwd)){
            json.add("result","原密码错误");
            return json.getJsonResult();
        }

        //新密码不能和原密码相同
        if(oldPwd.equals(newPwd)){
            json.add("result","新密码不能和原密码相同");
            return json.getJsonResult();
        }

        userDao.UpdatePassword(user.getUserAccount(),newPwd);
        json.add("result","success");
        return json.getJsonResult();
    }
    @Autowired
    private UserCodeDao userCodeDao;

    @Autowired
    private IUserEmailService userEmailService;
    //接口 forgot 忘记密码
    @RequestMapping("forgot")
    public ModelAndView forgotPassword(String account,String verifyCode,String password)
    {
        JsonBuilder json = new JsonBuilder();
        //IsAccountRegistered 返回的值是根据 account找到的元组数目，大于0则说明已经注册了
        boolean registered=userDao.IsAccountRegistered(account)>0?true:false;

        if(!registered) {
            json.add("success", "false");
            json.add("errCode", "803");
            json.add("errMsg", "您的账号尚未注册无法找回密码");
        }
        else{
            UserCode userCode = userCodeDao.queryUserCode(account);
            //空的话说明没有发送验证码
            if(userCode == null){
                json.add("success", "false");
                json.add("errCode", "804");
                json.add("errMsg", "您的账号尚未发送验证码 无法找回密码");
            }
            else{
                Long sendingTime = Long.valueOf(userCode.getSendingTime());
                String oldCode = userCode.getCode();
                //计算时间差
                Long checkingTime = Calendar.getInstance().getTimeInMillis();
                Long minute = (checkingTime - sendingTime) / (1000 * 60);
                //30分钟内且验证码正确
                if (minute <= 30 && verifyCode.equals(oldCode)) {
                    userDao.UpdatePassword(account,password);
                    json.add("success","true");
                }
                else {
                    json.add("success","false");
                    if(minute>30){
                        json.add("errCode","801");
                        json.add("errMsg","验证码已经失效");
                    }
                    else{
                        json.add("errCode","802");
                        json.add("errMsg","验证码输入错误");
                    }
                }
            }
        }
        return json.getJsonResult();
    }
}

