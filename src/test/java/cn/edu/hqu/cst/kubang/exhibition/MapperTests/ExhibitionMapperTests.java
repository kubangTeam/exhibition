package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class ExhibitionMapperTests {
    @Autowired
    private  Exhibition exhibition;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Before
    public void before(){
        //生成测试数据
        Date data = new Date();
        Date data1 = new Date();
        long value = data.getTime();
        long value1 = value + 10000;
        data.setTime(value);
        data1.setTime(value1);
        exhibition.setName("测试展会");
        exhibition.setStatus(1);
        exhibition.setStartTime(data);
        exhibition.setEndTime(data1);
        exhibition.setExhibitionHallId(1);
        //需要将该字段设为organizer表的外键，否则可能出现错误
        exhibition.setContractorId(1);

        if(exhibitionDao.saveExhibition(exhibition) ==1)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        //真删除 删除全部
//        if(exhibitionDao.deleteAll()==1)
//            System.out.println("删除成功");
        if(exhibitionDao.deleteById(exhibition.getId())==1)
            System.out.println("删除成功");

    }

    @Test
    public void testQueryAllExhibitions(){
        List<Exhibition> exhibitionList  =exhibitionDao.queryAllExhibitions();
        System.out.println(exhibitionList);
    }

    @Test
    public void testQueryExhibitionByID(){
        System.out.println(exhibitionDao.queryExhibitionByID(exhibition.getId()));
    }


    @Test
    public void testQueryExhibitionsByStatus(){
        System.out.println(exhibitionDao.queryExhibitionsByStatus(exhibition.getStatus()));
    }

    @Test
    public void testQueryExhibitionsByKeyWord(){
        String[] test = {"测试","1"};
        System.out.println(exhibitionDao.queryExhibitionsByKeyWord(test));
    }

    @Test
    public void testModifyExhibitionStatus(){
        System.out.println(exhibitionDao.modifyExhibitionStatus(exhibition.getId(),2));
    }

    @Test
    public void testModifyExhibition(){
        exhibition.setName("测试001");
        System.out.println(exhibitionDao.modifyExhibition(exhibition));
    }

}
