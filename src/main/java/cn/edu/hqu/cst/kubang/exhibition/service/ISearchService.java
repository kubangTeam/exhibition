package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Search;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.SearchServiceImpl;

import java.util.List;

public interface ISearchService {
    List<String> getHotSearch();
    int saveSearchRecord(int userId, String keyword, int classification);

}
