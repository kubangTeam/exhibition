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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        for(int i = 0;i<1;i++){
            Calendar startTime = Calendar.getInstance();
            startTime.set(2020,6-1,10);
            Calendar endTime = Calendar.getInstance();
            endTime.set(2020,12-1,10);
            Date data1 = startTime.getTime();
            Date data2 = endTime.getTime();
            exhibition.setName("测试展会");
            exhibition.setStatus(100);//测试用
            exhibition.setStartTime(data1);
            exhibition.setEndTime(data2);
            exhibition.setExhibitionHallId(1);
            //需要将该字段设为organizer表的外键，否则可能出现错误
            exhibition.setContractorId(1);
            exhibition.setSession(1);
            exhibition.setIntroduction("测试");
            exhibition.setPeriod("测试");
            exhibition.setPicture("/pic/data");
            if(exhibitionDao.saveExhibition(exhibition) ==1)
                System.out.println("添加数据成功");
        }
    }

    @After
    public void after(){
        int row  = exhibitionDao.deleteByStatus(100);
        if(row == 1)
            System.out.println("删除成功测试展会信息");

        int i = exhibitionSubareaDao.deleteExhibitionSubareaInfoByExhibitionId(exhibition.getId());
        System.out.println(i);
        if(i!=0)
            System.out.println("删除成功测试展会分区信息");
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



    @Test
    public void testHoldExhibition() throws Exception{
        int userId = 1;
       // String[] subAreaList = {"测试展区1","测试展区2","测试展区3"};
        List<String> subAreaList = new ArrayList<String>();
        subAreaList.add("测试1");
        subAreaList.add("测试2");
        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.multipart("/organizer/holdExhibition")
                .file(firstFile)
                .param("userId",String.valueOf(userId))
                .param("name",exhibition.getName())
                .param("startTime",String.valueOf(exhibition.getStartTime()))
                .param("endTime",String.valueOf(exhibition.getEndTime()))
                .param("exhibitionHallId",String.valueOf(exhibition.getExhibitionHallId()))
                .param("session",String.valueOf(exhibition.getSession()))
                .param("period",exhibition.getPeriod())
                .param("introduce",exhibition.getIntroduction())
                .param("subAreaList", String.valueOf(subAreaList)))
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
