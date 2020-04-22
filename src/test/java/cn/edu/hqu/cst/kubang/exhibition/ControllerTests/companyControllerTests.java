package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.CompanyController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = ExhibitionApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class companyControllerTests {
    @Autowired
    CompanyController companyController;

    @Autowired
    private MockMvc mockMvc;
// /company
// *  1、/identify 公司认证
// *  2、/getInformation 获取公司资料
// *  3、/updateInformation 修改商家资料
// *  4、/queryAttendedExhibition/{userId}/{pageNum} 商家查询自己公司的参加过的展会
    @Test
    public void testGetInformation() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/company/getInformation")
                .param("id","1"))
                .andReturn();
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
//      Assert.assertEquals(200,status);
//      Assert.assertEquals("success",content);
    }

}
