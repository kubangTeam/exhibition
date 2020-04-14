package cn.edu.hqu.cst.kubang.exhibition.ElasticsearchTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.GoodsRepository;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author SunChonggao
 * @Date 2020/4/13 15:20
 * @Version 1.0
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class esTest {
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testInsert() {
        //goodsRepository.save(goodsDao.selectGoodsById(1));
       // goodsRepository.save(goodsDao.selectGoodsById(3));
        System.out.println(elasticsearchService.searchExhibition("体育",0,1));

    }
}
