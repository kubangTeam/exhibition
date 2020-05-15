package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.AdminController;
import cn.edu.hqu.cst.kubang.exhibition.controller.AdvertisementController;
import cn.edu.hqu.cst.kubang.exhibition.dao.AdvertisementDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.junit.After;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

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

    @Autowired
    private AdvertisementDao advertisementDao;


    @Before
    public void before(){
        //advertisement.setId(id);
        Calendar startTime = Calendar.getInstance();
        startTime.set(2020,6-1,10);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2020,12-1,10);
        Date data1 = startTime.getTime();
        Date data2 = endTime.getTime();
        advertisement.setPriority(1);
        advertisement.setStartTime(data1);
        advertisement.setEndTime(data2);
        advertisement.setStatus(2);
        advertisement.setCompanyId(1);
        //advertisement.setOrganizerId();

        int i =  advertisementDao.insertAds(advertisement);
        if(i ==1){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }

    }

    @After
    public void after(){
        int i = advertisementDao.deleteAdsById(advertisement.getId());
        if(i ==1){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

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
        Assert.assertTrue(content.length()==8);//里面是一个Boolean 判断

    }



    @Test
    public void testUpdateAds() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/advertisement/updateAds")
                .param("id",String.valueOf(advertisement.getId()))
                .param("priority",String.valueOf(advertisement.getPriority()+1))
                .param("startTime",String.valueOf(advertisement.getStartTime()))
                .param("endTime",String.valueOf(advertisement.getEndTime())))
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
    public void testUpdateAdsStatus() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/advertisement/updateAdsStatus")
                .param("id",String.valueOf(advertisement.getId()))
                .param("status",String.valueOf(3)))
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
