package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
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
    private UserInformationDao userInformationDao;

    @Autowired
    private  UserInformation userInformation;

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

        userInformation.setUserAccount("11111111111");
        userInformation.setUserCompanyId(1);
        userInformation.setUserName("测试数据");
        userInformation.setUserPassword("测试数据密码");
        //用户权限 0 普通用户 1 管理员
        userInformation.setUserPermission(1);
        //用户积分
        userInformation.setUserIntegral(1000);
        //用户推荐码
        userInformation.setUserReccode("测试推荐吗");
        //承办方id
        userInformation.setUserOrganizerId(1);
        if(userInformationDao.UserRegisterFromPhoneNumber(userInformation) !=0)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        System.out.println(userInformation);
        int row = userInformationDao.deleteUserInformation(userInformation.getUserId());
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
        UserInformation row = userInformationDao.GetUseInfoFromAccount(userInformation.getUserAccount());
        System.out.println(row);
    }
    @Test
    public void testGetUserInfoFromId(){
        UserInformation row = userInformationDao.GetUserInfoFromId(userInformation.getUserId());
        System.out.println(row);
    }
    @Test
    public void testBindUserEmail(){
        int row = userInformationDao.bindUserEmail(userInformation.getUserId(),"测试邮箱");
        System.out.println(row);
    }

    @Test
    public  void testGetUserInfoFromOrganizerId(){
        List<UserInformation> row = userInformationDao.GetUserInfoFromOrganizerId(userInformation.getUserOrganizerId());
        System.out.println(row);
    }

}
