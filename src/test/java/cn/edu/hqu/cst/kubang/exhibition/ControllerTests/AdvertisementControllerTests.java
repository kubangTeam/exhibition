package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.AdminController;
import cn.edu.hqu.cst.kubang.exhibition.controller.AdvertisementController;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
@AutoConfigureMockMvc
public class AdvertisementControllerTests {
    @Autowired
    private AdvertisementController advertisementController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Advertisement advertisement;

    /**
     * 测试推荐广告横幅功能
     */
    @Test
    public void testRecommendAds() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/advertisement/get")
                .param("pageNum","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        //System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断

    }

}
