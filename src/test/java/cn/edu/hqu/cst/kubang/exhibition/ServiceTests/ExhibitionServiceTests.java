package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.ExhibitionController;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionSubareaDao;
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

import java.util.*;

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

    @Autowired
    ExhibitionSubareaDao exhibitionSubareaDao;

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
   /* @Before
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

*/

    @Test
    public void testQueryOngoingExhibitionInfo() throws Exception {
        //System.out.println(exhibitionService.queryOngoingExhibitionInfo());//整数页
//        System.out.println(exhibitionService.queryOngoingExhibitionInfo());//残余尾页
//        System.out.println(exhibitionService.queryOngoingExhibitionInfo());//异常页
        //System.out.println(exhibitionService.queryOnGoing().size());
        /*exhibitionService.deleteExhibitionIntoRedis();
        List<Exhibition> list = exhibitionService.queryOnGoing();
        for(Exhibition exhibition : list){
            exhibitionService.addExhibitionIntoRedis(exhibition.getId());
            System.out.println(exhibition.getId());
        }
        System.out.println(exhibitionService.getExhibitionIdInRedis());*/
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo(1));

    }

    @Test
    public void queryReadyToStartExhibitionInfo(){
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo(1));//整数页
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo(3));//残余尾页
        System.out.println(exhibitionService.queryReadyToStartExhibitionInfo(4));//异常页
    }

    @Test
    public void queryGoodsByExhibitionIdAndSubareaId(){
        System.out.println(exhibitionService.queryGoodsByExhibitionIdAndSubareaId(1,3,1));//整数页
        //System.out.println(exhibitionService.queryGoodsByExhibitionIdAndSubareaId(1,1,1));//残余尾页
        //System.out.println(exhibitionService.queryGoodsByExhibitionIdAndSubareaId(1,1,1));//异常页
    }



    @Test
    public void queryAllGoodsByExhibitionId(){
//        System.out.println(exhibitionService.queryAllGoodsByExhibitionId(1,1));
//        System.out.println(exhibitionService.queryAllGoodsByExhibitionId(1,10));
    }



    @Test
    public void queryAddExhibitionSubarea(){
        List<String> subAreaList = new ArrayList<String>();
        subAreaList.add("测试1");
        subAreaList.add("测试2");
        int i = exhibitionService.addSubareaInfo(subAreaList,exhibition.getId());
        System.out.println(i);
    }

}
