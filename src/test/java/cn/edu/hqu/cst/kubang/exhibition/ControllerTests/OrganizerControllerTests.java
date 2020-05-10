package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;


import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionSubareaDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
@AutoConfigureMockMvc
public class OrganizerControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Exhibition exhibition;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private ExhibitionSubarea exhibitionSubarea;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;

    @Before
    public void before(){
        //生成测试数据
        //1、提交展会分区信息



//        exhibition.setName("测试展会");
//        exhibition.setStatus(1);
//        exhibition.setStartTime();
//        exhibition.setEndTime();
//        exhibition.setExhibitionHallId();
//        exhibition.setContractorId();
//        exhibition.setSession();
//        exhibition.setPeriod();
//        exhibition.setIntroduction();
//        exhibition.setTel();
//        exhibition.setPicture();
//        exhibition.setSubareaId();
//
//        if(companyDao.addUnidentifiedCompanyInfo(company) ==1)
//            System.out.println("添加数据成功");
//    }
//
//    @After
//    public void after(){
//        if(companyDao.delete(company.getId())==1)
//            System.out.println("删除成功");
    }



    @Test
    public void testFindCityNameByExhibitionHallId() throws Exception{


//        @RequestParam(value = "id") int userId,
//        @RequestParam(value = "name") String name,
//        @RequestParam(value = "startTime") Date startTime,
//        @RequestParam(value = "endTime") Date endTime,
//        @RequestParam(value = "exhibitionHallId") int exhibitionHallId,
//        @RequestParam(value = "session") int session,
//        @RequestParam(value = "period") String period,
//        @RequestParam(value = "introduce") String introduce,
//        @RequestParam(value = "picture") MultipartFile file,
//        HttpServletRequest request



//        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
//        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
//                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
//        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.multipart("/organizer/holdExhibition")
//                .file(firstFile)
//                .param("userId","")
//                .param("name","")
//                .param("startTime","")
//                .param("endTime","")
//                .param("exhibitionHallId","")
//                .param("session","")
//                .param("period","")
//                .param("introduce","")
//                .param("picture",""))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        //查询展品推荐
//        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/organizer/holdExhibition")
//                .param("exhibitionHallId","1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        mvcResult.getResponse().setCharacterEncoding("UTF-8");
//        //mvcResult.andDo(print()).andExpect(status().isOk());
//        int status=mvcResult.getResponse().getStatus();
//        String content =mvcResult.getResponse().getContentAsString();
//        System.out.println(status);
//        System.out.println(content);
//        Assert.assertEquals(200,status);
//        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }


}
