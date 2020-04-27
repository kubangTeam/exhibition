package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionSubareaDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
        exhibitionSubarea.setSubarea("小学教育展区");
        exhibitionSubarea.setSubarea("中学教育展区");
        exhibitionSubarea.setSubarea("大学教育展区");
        int i = exhibitionSubareaDao.insertExhibitionSubareaInfo(exhibitionSubarea);

        exhibitionSubarea.setExhibitionId(2);
        exhibitionSubarea.setSubarea("男性用品专区");
        exhibitionSubarea.setSubarea("女性用品专区");
        int j = exhibitionSubareaDao.insertExhibitionSubareaInfo(exhibitionSubarea);
        if(i ==1 && j==1)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        //真删除 删除全部
//        if(exhibitionDao.deleteAll()==1)
//            System.out.println("删除成功");
//        if(exhibitionDao.deleteById(exhibition.getId())==1)
//            System.out.println("删除成功");

    }
}
