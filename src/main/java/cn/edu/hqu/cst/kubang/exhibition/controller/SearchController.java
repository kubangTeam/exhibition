package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/4/13 22:16
 * @Version 1.0
 * @Description:
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    IExhibitionService exhibitionService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public void initSearchData(){
        List<Goods> listGoods = goodsService.queryGoodsALl();
        for(Goods goods : listGoods)
            elasticsearchService.saveGoods(goods);
        List<Exhibition> listExhibition = exhibitionService.queryAll();
        for(Exhibition exhibition : listExhibition)
            elasticsearchService.saveExhibition(exhibition);

    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public Page<Goods> searchGoods(@RequestParam(value = "keyword") String keyword,
                                   @RequestParam(value = "pageNum") int pageNum,
                                   @RequestParam(value = "pageSize") int pageSize) {
        Page<Goods> result= elasticsearchService.searchGoods(keyword, pageNum, pageSize);
        return result;
    }
    @RequestMapping(value = "/exhibition", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public Page<Exhibition> searchExhibition(@RequestParam(value = "keyword") String keyword,
                                             @RequestParam(value = "pageNum") int pageNum,
                                             @RequestParam(value = "pageSize") int pageSize) {
        Page<Exhibition> result= elasticsearchService.searchExhibition(keyword, pageNum, pageSize);
        return result;
    }



}
