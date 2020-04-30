package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/4/13 22:16
 * @Version 1.0
 * @Description:
 *
 *
 * 接口简介
 * 1、/init  同步搜索服务器数据用的，不要点
 * 2、/goods/{num}  搜索展品, num=1/2/3,分别对应综合、热度、时间
 *
 * 商品 商家 展会
 */
@RestController
@RequestMapping("/search")
@Api(tags = "展会、展品和商家搜索")
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    IExhibitionService exhibitionService;

    @Autowired
    ExhibitionDao exhibitionDao;

    @Autowired
    private ISearchService searchService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    ICompanyService companyService;

    @ApiOperation(value = "添加所有展品数据到ES中",notes = "mysql to ES")
    @RequestMapping(value = "/init/goods", method = RequestMethod.GET)
    public ResponseJson<String> initGoodsSearchData(){
        List<Goods> listGoods = goodsService.queryGoodsALl();
        for(Goods goods : listGoods)
            elasticsearchService.saveGoods(goods);
        return new ResponseJson(true, "005", "操作成功","添加了"+listGoods.size()+"条数据");
    }
    @ApiOperation(value = "添加所有展会数据到ES中",notes = "mysql to ES")
    @RequestMapping(value = "/init/exhibition", method = RequestMethod.GET)
    public ResponseJson<String> initExhibitionSearchData(){
        List<Exhibition> listExhibition = exhibitionService.queryAll();
        System.out.println(listExhibition.get(0));
        for(Exhibition exhibition : listExhibition)
          elasticsearchService.saveExhibition(exhibition);
        return new ResponseJson(true, "005", "操作成功","添加了"+listExhibition.size()+"条数据");
    }
    @ApiOperation(value = "添加所有展会数据到ES中",notes = "mysql to ES")
    @RequestMapping(value = "/init/company", method = RequestMethod.GET)
    public ResponseJson<String> initCompanySearchData(){
        List<Company> listCompany = companyService.queryAll();
        //System.out.println(listCompany.get(0));
        for(Company company : listCompany)
            elasticsearchService.saveCompany(company);
        return new ResponseJson(true, "005", "操作成功","添加了"+listCompany.size()+"条数据");

    }
    @ApiOperation(value = "删除ES中的展品数据",notes = "如果数据库中字段或实体类更改，需要删除ES的原数据，否则会报错")
    @RequestMapping(value = "/delete/goods", method = RequestMethod.GET)
    public ResponseJson<String> deleteGoodsSearchData(){
         elasticsearchService.deleteAllGoods();
        return new ResponseJson(true, "005", "操作成功","删除了展品索引中的数据");
    }
    @ApiOperation(value = "删除ES中的展会数据",notes = "如果数据库中字段或实体类更改，需要删除ES的原数据，否则会报错")
    @RequestMapping(value = "/delete/exhibition", method = RequestMethod.GET)
    public ResponseJson<String> deleteExhibitionSearchData(){
        elasticsearchService.deleteAllExhibition();
        return new ResponseJson(true, "005", "操作成功","删除了展会索引中的数据");
    }
    @ApiOperation(value = "删除ES中的商家数据",notes = "如果数据库中字段或实体类更改，需要删除ES的原数据，否则会报错")
    @RequestMapping(value = "/delete/company", method = RequestMethod.GET)
    public ResponseJson<String> deleteCompanySearchData(){
        elasticsearchService.deleteAllCompany();
        return new ResponseJson(true, "005", "操作成功","删除了商家索引中的数据");
    }


    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展品", notes = "")
    @ApiImplicitParams({
           // @ApiImplicitParam(name = "num", value = "排序条件", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public ResponseJson<Goods> searchGoods(@RequestParam(value = "keyword") String keyword,
                                   //@PathVariable(value = "num") String num,
                                   @RequestParam(value = "pageNum") int pageNum,
                                   @RequestParam(value = "pageSize") int pageSize) {
       // String factor = numToFactor(Integer.valueOf(num),1);
        Page<Goods> result= elasticsearchService.searchGoods(keyword,  pageNum, pageSize);
        if(result == null)
            return new ResponseJson(false, "-008", "搜索结果为空", null);
        else
            return new ResponseJson(true, "005", "搜索成功", result);
    }
    @RequestMapping(value = "/exhibition", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展会", notes = "")
    @ApiImplicitParams({
           // @ApiImplicitParam(name = "num", value = "排序条件", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public ResponseJson<Exhibition> searchExhibition(//@PathVariable(value = "num") int num,
                                             @RequestParam(value = "keyword") String keyword,
                                             @RequestParam(value = "pageNum") int pageNum,
                                             @RequestParam(value = "pageSize") int pageSize) {
       // String factor = numToFactor(Integer.valueOf(num),0);
        Page<Exhibition> result= elasticsearchService.searchExhibition(keyword, pageNum, pageSize);
        if(result == null)
            return new ResponseJson(false, "-008", "搜索结果为空", null);
        else
            return new ResponseJson(true, "005", "搜索成功", result);
    }
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ApiOperation(value = "搜索商家", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public ResponseJson<Company> searchCompany(@RequestParam(value = "keyword") String keyword,
                                       @RequestParam(value = "pageNum") int pageNum,
                                       @RequestParam(value = "pageSize") int pageSize) {
        Page<Company> result= elasticsearchService.searchCompany(keyword, pageNum, pageSize);
        if(result == null)
            return new ResponseJson(false, "-008", "搜索结果为空", null);
        else
            return new ResponseJson(true, "005", "搜索成功", result);
    }

    /*private static String numToFactor(int num, int flag) {
        String factor = null;
        switch (num){
            case 1 : factor = flag == 0 ? "id" : "goodsId"; break;
            case 2 : factor = flag == 0 ? "exhibitionHallId" :"priority"; break;
            case 3 : factor = "startTime"; break;
        }
        return factor;
    }*/

    /**
     * 获取热门搜索
     */
    @ApiOperation(value = "获取热门搜索")
    @GetMapping("/hot/key")
    public List<String> getHotSearch() {
        return searchService.getHotSearch();
    }


}