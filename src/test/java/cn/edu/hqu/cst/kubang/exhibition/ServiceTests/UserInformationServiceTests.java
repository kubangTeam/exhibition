package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserInformationServiceImpl;
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
    private UserInformationServiceImpl userInformationService;


    @Test
    public void testIsCompanyOrNot(){
        if(userInformationService.isCompanyOrNot(1)!=0){
            System.out.println("该用户为商家");
            System.out.println("商家id为："+userInformationService.isCompanyOrNot(1));
        }else
        System.out.println("该用户不是商家");
    }
}
