package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.CompanyController;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = ExhibitionApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class companyControllerTests {
// *  1、/identify 公司认证
// *  3、/updateInformation 修改商家资料
// *  4、/queryAttendedExhibition/{userId}/{pageNum} 商家查询自己公司的参加过的展会
    @Autowired
    CompanyController companyController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Company company;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;


    @Before
    public void before(){
        //生成测试数据
        company.setName("测试数据");
        company.setType("农业");
        company.setAddress("测试地址");
        company.setWebsite("www.test.com");
        company.setTelephone("18162327341");
        company.setIntroduction("这是一条测试数据");
        company.setIdentifyStatus(1);//待审核
        company.setHeadPicture("/pic/data");
        if(companyDao.addUnidentifiedCompanyInfo(company) ==1)
            System.out.println("添加测试商家数据成功");


        user.setUserAccount("11111111111");
        //userInformation.setUserCompanyId(1);
        user.setUserName("测试数据");
        user.setUserPassword("测试数据密码");
        //用户权限 0 普通用户 1 管理员
        user.setUserPermission(1);
        //用户积分
        user.setUserIntegral(1000);
        //用户推荐码
        user.setUserReccode("测试推荐码");
        //承办方id
        user.setUserOrganizerId(1);
        //测试商家认证不同情况
        user.setUserCompanyId(company.getId());

        if(userDao.UserRegisterFromPhoneNumber(user) ==1)
            System.out.println("添加测试用户信息数据成功");
    }

    @After
    public void after(){
        int row = userDao.deleteUserInformation(user.getUserId());
        int row1 = companyDao.delete(company.getId());
        if(row==1 && row1 ==1)
            System.out.println("删除数据成功");
    }



    @Test
    public void testGetCompanyInformationById() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/company/getInformationByCompanyId")
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

        MvcResult mvcResult1=mockMvc.perform(MockMvcRequestBuilders.get("/company/getInformationByCompanyId")
                .param("id","100000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult1.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        status=mvcResult1.getResponse().getStatus();
        content =mvcResult1.getResponse().getContentAsString();
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
                .param("userId",String.valueOf(user.getUserId()))
                .param("name","测试代码")
                .param("address","测试地址")
                .param("website","www.test.com")
                .param("type","1")
                .param("introduce","这是一条测试代码")
                .param("tel","11111111111"))
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
    public void testUpdateInformation() throws Exception {

//        //转换为json
//        String requestJson = JSONObject.toJSONString(goods);
//        //查询热门展品 根据商品分类
//        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/goods/add")
//                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();

        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.multipart("/company/updateInformation")
                .file(firstFile)
                .param("companyId",String.valueOf(company.getId()))
                .param("name","测试代码")
                .param("address","测试地址")
                .param("website","www.update.com")
                .param("type","1")
                .param("introduce","这是一条测试代码")
                .param("tel","11111111111"))
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




}
