package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AdvertisementServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class AdvertisementServiceTests {
    @Autowired
    private AdvertisementServiceImpl advertisementService;

    @Test
    public void testRecommendAds(){
        System.out.println(advertisementService.recommendAds());
    }

    @Test
    public void testExamineAds(){
        System.out.println(advertisementService.examineAds(1,1));
    }
}
