package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.CompanyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class CompanyServiceTests {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;

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
    Map<String, Object> map = new HashMap<>();
    @After
    public void after(){
        int row = userDao.deleteUserInformation(user.getUserId());
        int line = companyDao.delete((int)map.get("company"));
        if(row==1 && line ==1)
            System.out.println("删除数据成功");
    }

    @Test
    public void testCompanyIdentify(){
        int id = user.getUserId();
        map = companyService.CompanyIdentify(id,"测试","测试地址","www.test.com",
                "1","This is a testData","11111111111","/pic/data");
        System.out.println(map);
        System.out.println(map.get("company"));
    }

    @Test
    public void testQueryCompanyAttendedExhibition(){
        List<Exhibition> result = companyService.queryCompanyAttendedExhibition(1);
        System.out.println(result);
    }
}
