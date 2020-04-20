package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyJoinExhibitionDao;
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
public class CompanyJoinExhibitionMapperTests {
    @Autowired
    private CompanyJoinExhibition companyJoinExhibition;

    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @BeforeClass
    public static void beforeClass(){

        System.out.println("before class");
    }

    @AfterClass
    public static void afterClass(){

        System.out.println("before class");
    }

    @Before
    public void before(){
        //生成测试数据
        //需要注意exhibitionId为外键，添加的测试数据的id需要在展会信息表中存在。
        companyJoinExhibition.setCompanyId(1);
        companyJoinExhibition.setExhibitionId(37);
        if(companyJoinExhibitionDao.insertCompanyJoinExhibition(companyJoinExhibition) ==1)
            System.out.println("添加数据成功");
    }

    @After
    public void after(){
        if(companyJoinExhibitionDao.delete(companyJoinExhibition.getId())==1)
            System.out.println("删除成功");
    }

    @Test
    public void testSelectCompanyIdByExhibitionId(){
        //当外键不存在的时候，查询会出错。
        List<CompanyJoinExhibition> rows =companyJoinExhibitionDao.selectCompanyIdByExhibitionId(37);
        System.out.println(rows);
    }
}
