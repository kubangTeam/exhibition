package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.AdminController;
import cn.edu.hqu.cst.kubang.exhibition.controller.CompanyController;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class AdminControllerTests {
    @Autowired
    private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Exhibition exhibition;

    @Test
    public void testQueryAllExhibition() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/Admin/queryAllExhibition/1"))
                //.param("pageNum","1"))
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
    public void testQueryExhibition() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/Admin/queryExhibitionByStatus/2/1"))//状态为1 页数为1
                //.param("pageNum","1"))
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
    public void testQueryExhibitionByKeyWord() throws Exception{

        String[] keyWord = {"测试","1"};
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/Admin/queryExhibitionByKeyWord")//状态为1 页数为1
                .param("pageNum","1")
                .param("keyWord",keyWord))
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
    public void testUpdateExhibitionInfo() throws Exception{

       //生成测试数据(数据库不为空的数据)
        Date data = new Date();
        Date data1 = new Date();
        long value = data.getTime();
        long value1 = value + 10000;
        data.setTime(value);
        data1.setTime(value1);
        exhibition.setId(11);
        exhibition.setName("测试展会修改");
        exhibition.setStatus(1);
        exhibition.setStartTime(data);
        exhibition.setEndTime(data1);
        exhibition.setExhibitionHallId(1);
        //需要将该字段设为organizer表的外键，否则可能出现错误
        exhibition.setContractorId(1);

        //转换为json
        String requestJson = JSONObject.toJSONString(exhibition);
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.put("/Admin/updateExhibitionInfo")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
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
    public void testUpdateExhibitionStatus() throws Exception{
       int id = 1;
       int exhibitionStatus = 2;
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.put("/Admin/updateExhibitionStatus")//状态为1 页数为1
                .param("id", "1299")
                .param("status","2"))
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
