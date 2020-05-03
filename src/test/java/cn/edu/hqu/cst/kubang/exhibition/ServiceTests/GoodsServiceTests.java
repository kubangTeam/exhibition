package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class GoodsServiceTests {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IExhibitionService exhibitionService;

    @Autowired
    private Company company;

    @Test
    public void testSelectCompanyInformationByGoodsId() throws Exception {
        /*company = goodsService.selectCompanyInformationByGoodsId(1);
        System.out.println(company);*/
        //System.out.println(exhibitionService.queryAllGoodsByExhibitionId(1));
        //System.out.println(goodsService.updateGoodsInRedis());
        //System.out.println(goodsService.getGoodsIdInRedis().size());
        List<Goods> list = goodsService.queryGoodsALl();
        System.out.println(list.size());
        for(Goods goods : list){
            goodsService.addGoodsIntoRedis(goods.getGoodsId());
        }

    }

    @Test
    public void testQueryAllGoodsByCategoryId(){
        System.out.println(goodsService.queryAllGoodsByCategoryId(1).size());
        System.out.println(goodsService.queryAllGoodsByCategoryId(1));
    }


}
