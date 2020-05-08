package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

/**
 * 测试邮箱的相关服务
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class EmailServiceTests {

    @Autowired
    private UserEmailServiceImpl userEmailService;

    @Autowired
    private UserCodeDao userCodeDao;

    @Autowired
    private UserCode userCode;

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;

    String to = "2502665955@qq.com";//收件人
    String subject ="测试";//主题
    String content = "1111";//内容
    @Before
    public void before(){
        String phoneNumber ="11111111111";
        String password = "sunquan";
        String recCode = "cedsad";
        user.setUserAccount(phoneNumber);
        user.setUserPassword(password);
        user.setUserName(phoneNumber);
        user.setUserPermission(0);
        user.setUserReccode(recCode);
        user.setUserIntegral(0);
        //System.out.println(userDao.UserRegisterFromPhoneNumber(user));
        if(userDao.UserRegisterFromPhoneNumber(user) ==1){
            System.out.println("用户手机号注册成功");
        }
        else{
            System.out.println("用户手机号注册失败");
        }

        int row = userEmailService.sendSimpleMail(to,subject,content);
        if(row ==200){
            System.out.println("邮件发送成功");
        }
        else{
            System.out.println("邮件发送失败");
        }

        userCode.setSendingTime(String.valueOf(Calendar.getInstance().getTimeInMillis()));//获取当前时间
        userCode.setCode(content);//邮箱验证码内容
        userCode.setAccount(to);//邮箱
        if(userCodeDao.saveUserCode(userCode)==1){
            System.out.println("邮件发送记录存储成功");
        }else{
            System.out.println("邮件发送记录存储失败");
        }
    }

    @After
    public void after(){
        if(userDao.deleteUserInformation(user.getUserId())==1)
            System.out.println("删除测试用户成功");
        else
            System.out.println("删除测试用户失败");

        if(userCodeDao.deleteUserCode(user.getUserAccount())==1){
            System.out.println("删除测试账户邮箱发送记录成功");
        }else
            System.out.println("删除测试账户邮箱发送记录成功");

    }

    @Test
    public void testIsUserEmailSingle(){
        if(userEmailService.isUserEmailSingle(to)==true)
            System.out.println("邮箱未被其他用户绑定");
        else
            System.out.println("邮箱已被其他用户绑定");
    }

    @Test
    public void testIsUserEmailBound(){
        if(userEmailService.isUserEmailBound(user.getUserId()) ==true)
            System.out.println("用户已绑定邮箱");
        else
            System.out.println("用户未绑定邮箱");
    }
    @Test
    public void testCheckCode(){
        if(userEmailService.checkCode(to,content) ==true)
            System.out.println("用户邮箱验证码填写正确");
        else
            System.out.println("用户邮箱验证码填写失败");
    }



}
