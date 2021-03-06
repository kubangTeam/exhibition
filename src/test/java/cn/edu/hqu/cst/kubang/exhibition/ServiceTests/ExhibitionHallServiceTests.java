package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.ExhibitionHallSeviceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class ExhibitionHallServiceTests {

    @Autowired
    private ExhibitionHallSeviceImpl exhibitionHallSevice;

    @Test
    public void testFindCityNameAndExhibitionNameByexhibitionId(){

        System.out.println(exhibitionHallSevice.findCityNameByExhibitionHallId(1));
    }
}
