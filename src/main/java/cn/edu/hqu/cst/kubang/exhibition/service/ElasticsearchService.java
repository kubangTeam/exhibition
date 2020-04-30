package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.CompanyRepository;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.ExhibitionRepository;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.GoodsRepository;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
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
import java.util.LinkedHashMap;
import java.util.Map;

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
    private CompanyRepository companyRepository;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    public void saveGoods(Goods goods) {
        goodsRepository.save(goods);
    }
    public void deleteGoods(int goodsId){
        goodsRepository.deleteById(goodsId);
    }
    public void deleteAllGoods(){
        goodsRepository.deleteAll();
    }
    public Page<Goods> searchGoods(String keyword, int current, int limit) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "goodsName", "goodsIntroduce"))    //构造搜索条件
                .withSort(SortBuilders.fieldSort("startTime").order(SortOrder.DESC))   //构造排序条件
                .withPageable(PageRequest.of(current, limit))     //构造分页条件
                .build();
       return elasticTemplate.queryForPage(searchQuery, Goods.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }
                ArrayList<Map<String, Object>> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    String goodsId = hit.getSourceAsMap().get("goodsId").toString();
                    map.put("goodsId", Integer.valueOf(goodsId));
                    String goodsName = hit.getSourceAsMap().get("goodsName").toString();
                    map.put("goodsName", goodsName);
                    String categoryId = hit.getSourceAsMap().get("categoryId").toString();
                    map.put("categoryId", Integer.valueOf(categoryId));
                    String currentPrice = hit.getSourceAsMap().get("currentPrice").toString();
                    map.put("currentPrice", currentPrice);
                    String image = hit.getSourceAsMap().get("image").toString();
                    map.put("image", image);
                    list.add(map);
                    /*Goods goods = new Goods();
                    String goodsId = hit.getSourceAsMap().get("goodsId").toString();
                    goods.setGoodsId(Integer.valueOf(goodsId));
                    String goodsName = hit.getSourceAsMap().get("goodsName").toString();
                    goods.setGoodsName(goodsName);
                    String categoryId = hit.getSourceAsMap().get("categoryId").toString();
                    goods.setCategoryId(Integer.valueOf(categoryId));
                    String goodsAreaNumber = hit.getSourceAsMap().get("goodsAreaNumber").toString();
                    goods.setGoodsAreaNumber(goodsAreaNumber);
                    String companyId = hit.getSourceAsMap().get("companyId").toString();
                    goods.setCompanyId(Integer.valueOf(companyId));
                    String goodsIntroduce = hit.getSourceAsMap().get("goodsIntroduce").toString();
                    goods.setGoodsIntroduce(goodsIntroduce);
                    String image = hit.getSourceAsMap().get("image").toString();
                    goods.setImage(image);*/
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
    public void deleteExhibition(int id){
        exhibitionRepository.deleteById(id);
    }
    public void deleteAllExhibition(){
        exhibitionRepository.deleteAll();
    }
    public Page<Exhibition> searchExhibition(String keyword, int current, int limit) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "name","introduction"))    //构造搜索条件
                //.withSort(SortBuilders.fieldSort(factor).order(SortOrder.DESC))   //构造排序条件,按开始时间降序
                .withPageable(PageRequest.of(current, limit))     //构造分页条件
                .build();
        return elasticTemplate.queryForPage(searchQuery, Exhibition.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }
                ArrayList<Map<String, Object>> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    String exhibitionId = hit.getSourceAsMap().get("id").toString();
                    map.put("exhibitionId", Integer.valueOf(exhibitionId));
                    String exhibitionName = hit.getSourceAsMap().get("name").toString();
                    map.put("exhibitionName", exhibitionName);
                    String introduction = hit.getSourceAsMap().get("introduction").toString();
                    map.put("introduction", introduction);
                    String startTime = hit.getSourceAsMap().get("startTime").toString();
                    map.put("startTime", startTime);
                    String picture = hit.getSourceAsMap().get("picture").toString();
                    map.put("picture", picture);
                    list.add(map);
                    /*Exhibition exhibition = new Exhibition();
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
                    list.add(exhibition);*/
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
    public void saveCompany(Company company) {companyRepository.save(company);
    }
    public void deleteCompany(int id){
        companyRepository.deleteById(id);
    }
    public void deleteAllCompany(){
        companyRepository.deleteAll();
    }
    public Page<Company> searchCompany(String keyword, int current, int limit) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "name"))    //构造搜索条件
                .withPageable(PageRequest.of(current, limit))     //构造分页条件
                .build();
        return elasticTemplate.queryForPage(searchQuery, Company.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }
                ArrayList<Map<String, Object>> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    String companyId = hit.getSourceAsMap().get("id").toString();
                    map.put("companyId", Integer.valueOf(companyId));
                    String companyName = hit.getSourceAsMap().get("name").toString();
                    map.put("companyName", companyName);
                    String headPicture = hit.getSourceAsMap().get("headPicture").toString();
                    map.put("headPicture", headPicture);
                    list.add(map);
                    /*Company company = new Company();
                    String id = hit.getSourceAsMap().get("id").toString();
                    company.setId(Integer.valueOf(id));
                    String name = hit.getSourceAsMap().get("name").toString();
                    company.setName(name);
                    String type = hit.getSourceAsMap().get("type").toString();
                    company.setType(type);
                    String website = hit.getSourceAsMap().get("website").toString();
                    company.setWebsite(website);
                    String telephone = hit.getSourceAsMap().get("telephone").toString();
                    company.setTelephone(telephone);
                    String introduction = hit.getSourceAsMap().get("introduction").toString();
                    company.setIntroduction(introduction);
                    String headPicture = hit.getSourceAsMap().get("headPicture").toString();
                    company.setHeadPicture(headPicture);
                    list.add(company);*/

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
