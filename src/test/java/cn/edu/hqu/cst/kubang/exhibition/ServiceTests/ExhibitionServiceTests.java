package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.ExhibitionController;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.ExhibitionServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class ExhibitionServiceTests {

    @Autowired
    ExhibitionServiceImpl exhibitionService;

    @Autowired
    Exhibition exhibition;

    @Autowired
    ExhibitionDao exhibitionDao;

//    @Before
//    public void before(){
//        //生成测试数据
//        for(int i = 0;i<4;i++){
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(2020,6-1,10);
//            Calendar endTime = Calendar.getInstance();
//            endTime.set(2020,12-1,10);
//            Date data1 = startTime.getTime();
//            Date data2 = endTime.getTime();
//            exhibition.setName("测试展会");
//            exhibition.setStatus(5);
//            exhibition.setStartTime(data1);
//            exhibition.setEndTime(data2);
//            exhibition.setExhibitionHallId(1);
//            //需要将该字段设为organizer表的外键，否则可能出现错误
//            exhibition.setContractorId(1);
//            if(exhibitionDao.saveExhibition(exhibition) ==1)
//                System.out.println("添加数据成功");
//        }
//    }

//    @After
//    public void after(){
//        int row  = exhibitionDao.deleteByStatus(5);
//        if(row == 4)
//            System.out.println("删除成功");
//    }

    @Test
    public void testQueryOngoingExhibitionInfo(){
        System.out.println(exhibitionService.queryOngoingExhibitionInfo().size());
        System.out.println(exhibitionService.queryOngoingExhibitionInfo());
    }

    @Test
    public void queryReadyToStartExhibitionInfo(){
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo().size());
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo());
    }


    @Test
    public void queryAllGoodsByExhibitionId(){
        System.out.println(exhibitionService.queryAllGoodsByExhibitionId(1298).size());
        System.out.println(exhibitionService.queryAllGoodsByExhibitionId(1298));
    }

}
