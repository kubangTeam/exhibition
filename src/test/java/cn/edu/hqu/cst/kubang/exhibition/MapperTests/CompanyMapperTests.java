package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyJoinExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class CompanyMapperTests {
    @Autowired
    private Company company;

    @Autowired
    private CompanyDao companyDao;

    @BeforeClass
    public static void beforeClass(){

        System.out.println("before class");
    }

    @AfterClass
    public static void afterClass(){

        System.out.println("before class");
    }

   /* @Before
    public void before(){
        //生成测试数据
        company.setName("测试数据");
        company.setType("农业");
        company.setAddress("测试地址");
        company.setWebsite("www.test.com");
        company.setTelephone("18162327341");
        company.setIntroduction("这是一条测试数据");
        company.setIdentifyStatus(1);
        if(companyDao.addUnidentifiedCompanyInfo(company) ==1)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        if(companyDao.delete(company.getId())==1)
            System.out.println("删除成功");
    }*/

    @Test
    public void testGetUnidentifiedCompanies(){
        System.out.println(companyDao.getCompaniesByIdentifyStatus(company.getIdentifyStatus())
        );
    }

    @Test
    public void testIdentifyCompany(){
       int row = companyDao.identifyCompany(company.getId());
       System.out.println(row);
    }

    @Test
    public void testSelectCompanyInformationById(){
        Company test= companyDao.selectCompanyInformationById(company.getId());
        System.out.println(test);
    }

    @Test
    public void testUpdateCompanyInformation(){
        //生成同id company数据
        Company test = new Company();
        test.setId(company.getId());
        test.setName("测试数据update");
        test.setType("农业update");
        test.setAddress("测试地址");
        test.setWebsite("www.test.com");
        test.setTelephone("18162327341");
        test.setIntroduction("这是一条测试数据");
        int row = companyDao.updateCompanyInformation(test);
        System.out.println(row);
    }
    @Test
    public void testQueryAll(){
        System.out.println(companyDao.selectAll().size());
        System.out.println(companyDao.selectAll());
    }

    @Test
    public void tesGetByIdentifyStatus(){
        System.out.println(companyDao.getCompaniesByIdentifyStatus(5).size());
        System.out.println(companyDao.getCompaniesByIdentifyStatus(5));
    }


}
