package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class userInformationTests  {
    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @BeforeClass
    public static void beforeClass(){

        System.out.println("测试用户信息实体类DAO层功能");
    }

    @AfterClass
    public static void afterClass(){

        System.out.println("测试用户信息实体类DAO层功能结束");
    }

    @Before
    public void before(){
        //生成测试数据
        user.setUserAccount("11111111111");
        //userInformation.setUserCompanyId(1);
        user.setUserName("测试数据");
        user.setUserPassword("测试数据密码");
        //用户权限 0 普通用户 1 管理员
        user.setUserPermission(1);
        //用户积分
        user.setUserIntegral(1000);
        //用户推荐码
        user.setUserReccode("测试推荐吗");
        //承办方id
        user.setUserOrganizerId(1);

        if(userDao.UserRegisterFromPhoneNumber(user) !=0)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        int row = userDao.deleteUserInformation(user.getUserId());
        if(row==1)
            System.out.println("删除数据成功");
    }

    @Test
    public void testUserRegisterFromEmail(){
        //void UserRegisterFromEmail(String email, String password,String RecCode);
    }

    @Test
    public void testGetUseInfoFromAccount(){
        //容易测试出错，当不同userId的账号相同时会报错。可将返回结果改为列表
        User row = userDao.GetUseInfoFromAccount(user.getUserAccount());
        System.out.println(row);
    }

    @Test
    public void testGetUserInfoFromId(){
        User row = userDao.GetUserInfoFromId(user.getUserId());
        System.out.println(row.getUserCompanyId());
        System.out.println(row);
    }
    @Test
    public void testBindUserEmail(){
        int row = userDao.bindUserEmail(user.getUserId(),"测试邮箱");
        System.out.println(row);
    }

    @Test
    public  void testGetUserInfoFromOrganizerId(){
        List<User> row = userDao.GetUserInfoFromOrganizerId(user.getUserOrganizerId());
        System.out.println(row);
    }

    @Test
    public void testSetCompanyId(){
        int row  = userDao.setCompanyId(user.getUserId(),1000);
        System.out.println(userDao.GetUserInfoFromId(user.getUserId()));
        System.out.println(row);
    }

}
