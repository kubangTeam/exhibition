package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.controller.SMSController;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class SMSControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SMSController smsController;

    @Autowired
    private UserCodeDao userCodeDao;

    @Autowired
    private UserCode userCode;

    @Autowired
    private IShortMessageService iShortMessageService;

    @Autowired
    private UserDao userDao;

    @Before
    public void before(){
        String phoneNumber = "17338719294";
        int verifyCode = iShortMessageService.sendShortMessage(phoneNumber);

        //测试保存
        userCode.setAccount(phoneNumber);
        userCode.setCode(String.valueOf(verifyCode));
        String sendingTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        userCode.setSendingTime(sendingTime);
        if(iShortMessageService.saveUserCode(userCode)==1)
            System.out.println("保存成功");
    }

    @After
    public void after(){
        if(userDao.deleteUserInformationByAccount( "17338719294")==1)
            System.out.println("删除账号成功");
        if((Integer)userCodeDao.deleteUserCode(userCode.getAccount())!=null)
            System.out.println("删除记录成功");
    }

    @Test
    public void testQueryAllExhibition() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/SMS/send")
                .param("phoneNumber",userCode.getAccount()))
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
    public void testRegisterCheckCode() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/SMS/check/register")
                .param("phoneNumber",userCode.getAccount())
                .param("password","测试密码")
                .param("verifyCode",userCode.getCode())
                .param("recCode","123"))
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
