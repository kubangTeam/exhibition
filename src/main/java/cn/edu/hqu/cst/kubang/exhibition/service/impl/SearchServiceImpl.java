package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.SearchDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Search;
import cn.edu.hqu.cst.kubang.exhibition.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 21:20
 * @description: 展品、展会搜索
 **/

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<String> getHotSearch() {
        return goodsDao.queryHotSearchKey();
    }

    @Override
    public int saveSearchRecord(int userId, String keyword, int classification){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Search search = new Search();
        search.setUserId(userId);
        search.setSearchEntry(keyword);
        search.setSearchTime(timestamp);
        search.setClassification(classification);
        return searchDao.insertSearchRecord(search);
    }
}
