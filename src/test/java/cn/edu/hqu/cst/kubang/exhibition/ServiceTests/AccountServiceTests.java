package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AdvertisementServiceImpl;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试与个人账号相关服务
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class AccountServiceTests {



    @Autowired
    private AccountServiceImp accountServiceImp;

    @Test
    public void test(){
        accountServiceImp.registerFromEmail("2502665955@qq.com","1111111","hduisa");
    }


    @Test
    public void testIsCompanyOrNot(){
        System.out.println(accountServiceImp.isCompanyOrNot(669));

    }

    @Test
    public void testIsOrganizerOrNot(){
        System.out.println(accountServiceImp.isOrganizerOrNot(1));

    }
}
