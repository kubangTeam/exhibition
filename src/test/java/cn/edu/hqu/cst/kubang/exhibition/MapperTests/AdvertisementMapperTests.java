package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.AdvertisementDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @Author sunquan
 * @Date 2020/3/22 14.47
 * @Version 1.0
 * @Description:测试广告实体类DAO层功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class AdvertisementMapperTests {

    @Autowired
    private AdvertisementDao advertisementDao;

    @BeforeClass
    public static void beforeClass(){

        System.out.println("before class");
    }

    @AfterClass
    public static void afterClass(){

        System.out.println("before class");
    }

    Advertisement organizerAdvertisement = new Advertisement();
    Advertisement companyAdvertisement = new Advertisement();
    @Before
    public void before(){
        //生成测试数据
        Date data = new Date();
        Date data1 = new Date();
        long value = data.getTime();
        long value1 = value + 10000;
        data.setTime(value);
        data1.setTime(value1);

        companyAdvertisement.setCompanyId(1);
        companyAdvertisement.setStartTime(data);
        companyAdvertisement.setEndTime(data1);
        companyAdvertisement.setPicture("www.company.com");//图片url
        companyAdvertisement.setPriority(2);//高优先级
        companyAdvertisement.setStatus(2);// 已审核
        advertisementDao.insertAds(companyAdvertisement);

        organizerAdvertisement.setOrganizerId(1);
        //organizerAdvertisement.setCompanyId(100);
        organizerAdvertisement.setStartTime(data);
        organizerAdvertisement.setEndTime(data1);
        organizerAdvertisement.setPicture("www.organizer.com");//图片url
        organizerAdvertisement.setPriority(2);//高优先级
        organizerAdvertisement.setStatus(2);// 已审核
        advertisementDao.insertAds(organizerAdvertisement);
    }

    @After
    public void after(){
        advertisementDao.updateAdsStatus(companyAdvertisement.getId(),4);//4 删除状态
        advertisementDao.updateAdsStatus(organizerAdvertisement.getId(),4);//4 删除状态
    }

    @Test
    public void testFindAll(){
        List<Advertisement>rows = advertisementDao.selectAllAds();
        System.out.println(rows);
    }
    @Test
    public void testFindByStatus(){
        List<Advertisement>rows = advertisementDao.selectByAdsStatus(2);
        System.out.println(rows.size());
        System.out.println(rows);
    }
    @Test
    public void testFindByPriority(){
        List<Advertisement>rows = advertisementDao.selectByAdsPriority(2);
        System.out.println(rows.size());
        System.out.println(rows);

    }
    @Test
    public void testFindCompanyId(){
        List<Advertisement> advertisement = advertisementDao.selectByCompanyId(companyAdvertisement.getCompanyId());
        //Advertisement result = advertisement.get(0);
        System.out.println(advertisement.size());
        Assert.assertNotNull(advertisement.size());
        //Assert.assertEquals(result.getPicture(),"www.company.com");
    }
    @Test
    public void testFindOrganizerId(){
        List<Advertisement> advertisement = advertisementDao.selectByCompanyId(organizerAdvertisement.getOrganizerId());
        //Advertisement result = advertisement.get(0);
        System.out.println(advertisement.size());
        Assert.assertNotNull(advertisement);
        //Assert.assertEquals(result.getPicture(),"www.organizer.com");
    }

    @Test
    public void testUpdateAds(){
        Date data = new Date();
        Date data1 = new Date();
        long value = data.getTime();
        long value1 = value + 20000;
        data.setTime(value);
        data1.setTime(value1);
        //修改时间、图片、优先级
        int row = advertisementDao.updateAds(companyAdvertisement.getId(),data,data1,"www.companyUpdate.com",2);
        System.out.println(row);
    }

}
