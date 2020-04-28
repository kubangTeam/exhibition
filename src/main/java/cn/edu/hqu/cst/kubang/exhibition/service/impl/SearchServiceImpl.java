package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<String> getHotSearch() {
        return goodsDao.queryHotSearchKey();
    }
}
