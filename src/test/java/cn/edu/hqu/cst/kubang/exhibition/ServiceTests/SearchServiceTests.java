package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.Search;
import cn.edu.hqu.cst.kubang.exhibition.service.ISearchService;
import org.elasticsearch.search.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author SunChonggao
 * @Date 2020/5/1 15:20
 * @Version 1.0
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class SearchServiceTests {
    @Autowired
    ISearchService searchService;

    @Test
    public void saveTest(){
        Search search = new Search();
        search.setSearchEntry("test");
        search.setUserId(1);
        //search.setSearchTime()
        search.setClassification(1);

       // searchService.saveSearchRecord(search);
        System.out.println(search);
    }
}
