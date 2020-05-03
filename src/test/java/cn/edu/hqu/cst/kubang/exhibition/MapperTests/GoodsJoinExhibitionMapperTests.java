package cn.edu.hqu.cst.kubang.exhibition.MapperTests;


import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyJoinExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsJoinExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsJoinExhibition;
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
public class GoodsJoinExhibitionMapperTests {

//    int checkGoodsJoinOrNot(int exhibitionId,int goodsId);//根据商品id和展会id，查询该商品是否参加了该展会
//    int checkGoodsSubarea(int exhibitionId,int goodsId,int subareaId);//根据商品id、展会id、二级分类id，查询该商品是否参加了该展会且属于该二级分类

    @Autowired
    private GoodsJoinExhibition goodsJoinExhibition;

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

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
        //需要保证exhibitionId、goodsId、subAreaId都存在
        //展会id为1298 展区为9 商品id 52为公司id 1的商品
        goodsJoinExhibition.setExhibitionId(1);
        goodsJoinExhibition.setGoodsId(52);
        goodsJoinExhibition.setSubareaId(8);

        int row = goodsJoinExhibitionDao.insertGoodsJoinExhibition(goodsJoinExhibition);
        if(row ==1){
            System.out.println("添加数据成功");
        }
    }

    @After
    public void after(){
        if(goodsJoinExhibitionDao.deleteGoodsJoinExhibition(goodsJoinExhibition.getId())==1)
            System.out.println("删除成功");
    }

    @Test
    public void testCheckGoodsSubarea(){
        //根据商品id、展会id、二级分类id，查询该商品是否参加
        GoodsJoinExhibition goodsJoinExhibition1 = goodsJoinExhibitionDao.checkGoodsSubarea(goodsJoinExhibition.getExhibitionId(),goodsJoinExhibition.getGoodsId()
                ,goodsJoinExhibition.getSubareaId());
        if(goodsJoinExhibition1!=null){
            System.out.println(goodsJoinExhibition1);
            System.out.println("该数据存在");
        }else
            System.out.println("该数据不存在");

    }


}
