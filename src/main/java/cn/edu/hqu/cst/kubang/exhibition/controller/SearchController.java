package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
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
 */
@RestController
@RequestMapping("/search")
@Api(tags = "展会和展品搜索")
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    IExhibitionService exhibitionService;

    @Autowired
    private GoodsService goodsService;
    @ApiOperation(value = "同步搜索服务器数据用的，不要点")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public void initSearchData(){
        List<Goods> listGoods = goodsService.queryGoodsALl();
        for(Goods goods : listGoods)
            elasticsearchService.saveGoods(goods);
        List<Exhibition> listExhibition = exhibitionService.queryAll();
        for(Exhibition exhibition : listExhibition)
            elasticsearchService.saveExhibition(exhibition);

    }

    @RequestMapping(value = "/goods/{num}", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展品", notes = "num=1/2/3,分别对应综合、热度、时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "排序条件", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public Page<Goods> searchGoods(@RequestParam(value = "keyword") String keyword,
                                   @PathVariable(value = "num") String num,
                                   @RequestParam(value = "pageNum") int pageNum,
                                   @RequestParam(value = "pageSize") int pageSize) {
        String factor = numToFactor(Integer.valueOf(num));
        Page<Goods> result= elasticsearchService.searchGoods(keyword, factor,  pageNum, pageSize);
        return result;
    }
    @RequestMapping(value = "/exhibition/{num}", method = RequestMethod.GET)
    @ApiOperation(value = "搜索展会", notes = "num=1/2/3,分别对应综合、热度、时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "排序条件", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    public Page<Exhibition> searchExhibition(@PathVariable(value = "num") int num,
                                             @RequestParam(value = "keyword") String keyword,
                                             @RequestParam(value = "pageNum") int pageNum,
                                             @RequestParam(value = "pageSize") int pageSize) {
        String factor = numToFactor(num);
        Page<Exhibition> result= elasticsearchService.searchExhibition(keyword, factor, pageNum, pageSize);
        return result;
    }

    private static String numToFactor(int num) {
        String factor = null;
        switch (num){
            case 1 : factor = "goodsId"; break;
            case 2 : factor = "priority"; break;
            case 3 : factor = "startTime"; break;
        }
        return factor;
    }


}