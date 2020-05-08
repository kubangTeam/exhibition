package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.BindEmailController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
@AutoConfigureMockMvc
public class BindEmailControllerTests {

    @Autowired
    private BindEmailController bindEmailController;
    @Autowired
    private MockMvc mockMvc;


//    @Before
//    public void before() throws Exception {
//        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/email/send/bind")
//                .param("userId","1")
//                .param("to","2502665955@qq.com"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        mvcResult.getResponse().setCharacterEncoding("UTF-8");
//        //mvcResult.andDo(print()).andExpect(status().isOk());
//        int status=mvcResult.getResponse().getStatus();
//        String content =mvcResult.getResponse().getContentAsString();
//        System.out.println(status);
//        System.out.println(content);
//        Assert.assertEquals(200,status);
//        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
//    }

    @After
    public void after(){}

    @Test
    public void testBindSendEmail() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/email/send/bind")
                .param("userId","1")
                .param("to","2502665955@qq.com"))
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
    public void testRegisterSendEmail() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/email/send/emailRegister")
                .param("to","2502665955@qq.com"))
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
    public void testBindCheckCode() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/email/check/bind")
                .param("userId","1")
                .param("email","2502665955@qq.com")
                .param("newCode","3b14e1ee"))
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
    public void testBindCheckRegister() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/email/check/register")
                .param("pwd","111111111")
                .param("email","2502665955@qq.com")
                .param("code","f6935766")
                .param("recCode","hduisa"))
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
