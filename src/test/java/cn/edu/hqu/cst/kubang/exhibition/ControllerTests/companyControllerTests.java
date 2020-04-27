package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.CompanyController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = ExhibitionApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class companyControllerTests {
    // /company
// *  1、/identify 公司认证
// *  2、/getInformation 获取公司资料
// *  3、/updateInformation 修改商家资料
// *  4、/queryAttendedExhibition/{userId}/{pageNum} 商家查询自己公司的参加过的展会
    @Autowired
    CompanyController companyController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetInformation() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/company/getInformation")
                .param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testIdentify() throws Exception {
        //file为营业执照
        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.multipart("/company/identify")
                .file(firstFile)
                .param("userId","1")
                .param("name","测试代码")
                .param("address","测试地址")
                .param("website","www.test.com")
                .param("type","1")
                .param("introduce","这是一条测试代码"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }
    @Test
    public void testUpdateInformation(){

    }





}
