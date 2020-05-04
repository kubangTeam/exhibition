package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.AdvertisementDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author sunquan
 * @Date 2020/4/22 11.00
 * @Version 1.0
 * @Description:测试验证码实体类DAO层功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class UserCodeMapperTests {
    @Autowired
    private UserCode userCode;

    @Autowired
    private UserCodeDao userCodeDao;

    @BeforeClass
    public static void beforeClass(){

        System.out.println("测试验证码实体类DAO层功能");
    }

    @AfterClass
    public static void afterClass(){

        System.out.println("测试验证码实体类DAO层功能结束");
    }

    @Before
    public void before(){
        //生成测试数据
        String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        userCode.setAccount("1111111111");
        userCode.setCode("测试验证码");
        userCode.setSendingTime(sendingTime);
        if( userCodeDao.saveUserCode(userCode)==1)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        if(userCodeDao.deleteUserCode(userCode.getAccount())==1)
            System.out.println("删除数据成功");
    }

    @Test
    public void testQueryUserCode(){
       userCode = userCodeDao.queryUserCodeByAccount(userCode.getAccount());
       System.out.println(userCode);
    }
}
