package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class CompanyServiceTests {

    @Autowired
    private CompanyService companyService;

    @Test
    public void testCompanyIdentify(){
        String result = companyService.CompanyIdentify(2,"测试","测试地址","www.test.com",
                "1","This is a testData","/pic/load","/test.jpg");
        System.out.println(result);
    }

    @Test
    public void testQueryCompanyAttendedExhibition(){
        List<Exhibition> result = companyService.queryCompanyAttendedExhibition(1);
        System.out.println(result);
    }
}
