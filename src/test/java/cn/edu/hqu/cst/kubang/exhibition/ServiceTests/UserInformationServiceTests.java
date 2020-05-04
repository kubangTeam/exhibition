package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import cn.edu.hqu.cst.kubang.exhibition.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class UserInformationServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;

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
        System.out.println(userDao.UserRegisterFromPhoneNumber(user));
    }
    @After
    public void after(){
        if(userDao.deleteUserInformation(user.getUserId())==1)
            System.out.println("删除成功");
    }
    @Test
    public void testIsCompanyOrNot(){
        if(userService.isCompanyOrNot(1)!=0){
            System.out.println("该用户为商家");
            System.out.println("商家id为："+userService.isCompanyOrNot(1));
        }else
        System.out.println("该用户不是商家");
    }

    @Test
    public void testRegisterByPhoneNumber(){
        if(userService.registerByPhoneNumber(user.getUserAccount(),user.getUserPassword(),user.getUserReccode())==1)
            System.out.println("插入成功");
    }
}
