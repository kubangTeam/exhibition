package cn.edu.hqu.cst.kubang.exhibition.MapperTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.GoodsController;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author SunChonggao
 * @Date 2020/2/18 16:16
 * @Version 1.0
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class goodsMapperTest {
    @Autowired
     private GoodsDao goodsDao;
    @Autowired
     private GoodsController goodsController;
    @Test
    public void testSelectGoods(){
        //Goods goods = goodsDao.selectGoodsById(3);
        //System.out.println(goods);
        //System.out.println(goodsController.getRandomNumList(10,0,100));
        List<Map<String, Object>> list = new ArrayList<>();
        list = goodsController.getRecommendGoods();
        for(int i = 0;i < list.size();i++){
           System.out.println(list.get(i));
        }
        //System.out.println(goodsDao.selectStatusById(1));
    }
}