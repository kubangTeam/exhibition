package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.ExhibitionRepository;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.GoodsRepository;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author SunChonggao
 * @Date 2020/4/13 16:58
 * @Version 1.0
 * @Description:
 */
@Service
public class ElasticsearchService {
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    public void saveGoods(Goods goods) {
        goodsRepository.save(goods);
    }
    public void deleteGoods(int goodsId){
        goodsRepository.deleteById(goodsId);
    }
    public Page<Goods> searchGoods(String keyword, int current, int limit) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "goodsName", "goodsIntroduce"))    //构造搜索条件
                .withSort(SortBuilders.fieldSort("priority").order(SortOrder.DESC))   //构造排序条件
                .withPageable(PageRequest.of(current, limit))     //构造分页条件
                .build();
       return elasticTemplate.queryForPage(searchQuery, Goods.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }
                ArrayList<Goods> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Goods goods = new Goods();
                    String goodsId = hit.getSourceAsMap().get("goodsId").toString();
                    goods.setGoodsId(Integer.valueOf(goodsId));
                    String goodsName = hit.getSourceAsMap().get("goodsName").toString();
                    goods.setGoodsName(goodsName);
                    String categoryId = hit.getSourceAsMap().get("categoryId").toString();
                    goods.setCategoryId(Integer.valueOf(categoryId));
                    String goodsAreaNumber = hit.getSourceAsMap().get("goodsAreaNumber").toString();
                    goods.setGoodsAreaNumber(goodsAreaNumber);
                    String companyId = hit.getSourceAsMap().get("companyId").toString();
                    goods.setCompanyId(Integer.valueOf(categoryId));
                    String goodsIntroduce = hit.getSourceAsMap().get("goodsIntroduce").toString();
                    goods.setGoodsIntroduce(goodsIntroduce);
                    list.add(goods);
                }
                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), hits.getMaxScore());
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });
    }
    public void saveExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }
    public void deleteExhibition(int Id){
        exhibitionRepository.deleteById(Id);
    }
    public Page<Exhibition> searchExhibition(String keyword, int current, int limit) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "name","introduction"))    //构造搜索条件
                .withSort(SortBuilders.fieldSort("startTime").order(SortOrder.DESC))   //构造排序条件,按开始时间降序
                .withPageable(PageRequest.of(current, limit))     //构造分页条件
                .build();
        return elasticTemplate.queryForPage(searchQuery, Exhibition.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }
                ArrayList<Exhibition> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Exhibition exhibition = new Exhibition();
                    String Id = hit.getSourceAsMap().get("id").toString();
                    exhibition.setId(Integer.valueOf(Id));
                    String name = hit.getSourceAsMap().get("name").toString();
                    exhibition.setName(name);
                    String exhibitionHallId = hit.getSourceAsMap().get("exhibitionHallId").toString();
                    exhibition.setExhibitionHallId(Integer.valueOf(exhibitionHallId));
                    String showRoom = hit.getSourceAsMap().get("showRoom").toString();
                    exhibition.setShowRoom(showRoom);
                    String introduction = hit.getSourceAsMap().get("introduction").toString();
                    exhibition.setIntroduction(introduction);
                    String picture = hit.getSourceAsMap().get("picture").toString();
                    exhibition.setPicture(picture);
                    list.add(exhibition);
                }
                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), hits.getMaxScore());
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });
    }
}
