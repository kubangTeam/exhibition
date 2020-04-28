package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionSubareaDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
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
public class ExhibitionSubareaMapperTests {
    @Autowired
    private ExhibitionSubarea exhibitionSubarea;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;



    @Before
    public void before(){
        //生成测试数据
        exhibitionSubarea.setExhibitionId(1);
        exhibitionSubarea.setSubarea("测试展区");
        int i = exhibitionSubareaDao.insertExhibitionSubareaInfo(exhibitionSubarea);
        if(i ==1)
            System.out.println("添加数据成功");
        else
            System.out.println("添加数据失败");
    }

    @After
    public void after(){
        int row =  exhibitionSubareaDao.deleteExhibitionSubareaInfoById(exhibitionSubarea.getId());
        if(row ==1){
            System.out.println("删除成功");
        }else
            System.out.println("删除失败");

    }


//    List<ExhibitionSubarea> selectByExhibitionId(int exhibitionId);
//
//    int insertExhibitionSubareaInfo(ExhibitionSubarea exhibitionSubarea);
//
//    int deleteExhibitionSubareaInfoByExhibitionId(int exhibitionId);//删除测试数据，测试用

    @Test
    public void testSelectByExhibitionId(){
        List<ExhibitionSubarea> exhibitionSubareasList  = exhibitionSubareaDao.selectByExhibitionId(1);
        System.out.println(exhibitionSubareasList.size());
        System.out.println(exhibitionSubareasList);
    }

}
