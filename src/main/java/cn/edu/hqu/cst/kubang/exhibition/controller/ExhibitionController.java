package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.*;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

import cn.edu.hqu.cst.kubang.exhibition.service.impl.ExhibitionServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 14:09
 * @Description:
 * 展会方相关功能
 * 1、查看自己的展会信息
 * 2、申请广告
 * 3、
 *
 *
 * 接口简介
 * 1、/queryExhibitionSubareaById/{id} 根据展会id查询展会分区信息
 * 2、/queryGoodsByExhibitionId/{id} 据展会id查询展会的所有商品
 * 3、/querySubareaGoodsByExhibitionId/{id} 根据展会id和分区id查询展会分区商品信息
 * 4、/queryReadyToStartExhibitionInfo 返回即将上线的展会信息
 * 5、/ExhibitionDetails/id 根据id查询展会详情
 * 6、/queryOngoingExhibitionInfo  返回展会页面四个进行中的展会信息
 * 7、
 *
 */
@RestController
@RequestMapping("/exhibition")
@Api(tags = "展会方相关功能")
public class ExhibitionController {

    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;

    @Autowired
    private ExhibitionServiceImpl exhibitionService;
    @Autowired
    private Exhibition exhibition;
    @Autowired
    private ExhibitionDao exhibitionDao;




    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个

    /**
     * 根据展会id返回展会分区信息
     */
    @ApiOperation(value = "根据展会id查询展会分区信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryExhibitionSubareaById")
    public Map<String, Object> subareaInformation(@RequestParam(value = "exhibitionId")int exhibitionId) {
        //List<ExhibitionSubarea> subInformation = new ArrayList<ExhibitionSubarea>();
        Map<String, Object> map = new HashMap<>();
        List<ExhibitionSubarea> subInformation = exhibitionSubareaDao.selectByExhibitionId(exhibitionId);
        ExhibitionNew exhibition = exhibitionDao.queryExhibitionDetailsByID(exhibitionId);
        if(subInformation!=null && exhibition!=null){
            map.put("subInformation",subInformation);
        }else {
            String value = "该商家分区信息不存在";
            map.put("response", value);
        }
        return map;
    }


    /**
     * 根据展会id查询展会的所有商品
     * @return
     */
    @ApiOperation(value = " 根据展会id查询展会的所有商品", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryGoodsByExhibitionId")
    public PageInfo<Goods> queryGoodsByExhibitionId(@RequestParam(value = "exhibitionId")int exhibitionId,
                                            @RequestParam(value = "pageNum") int pageNum,
                                            @RequestParam(value = "pageSize") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Goods> pageInfo = new PageInfo<>(exhibitionService.queryAllGoodsByExhibitionId(exhibitionId));
        return pageInfo;
    }


    /**
     * 根据展会id查询展会id和二级分类id查询商品
     * @return
     */
    @ApiOperation(value = "根据展会id查询展会id和二级分类id查询商品", notes = "展会Id+分区Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "subareaId", value = "分区id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/querySubareaGoodsByExhibitionId")
    public PageInfo<Goods> allSubareaGoodById(@RequestParam(value = "exhibitionId")int exhibitionId,
                                              @RequestParam(value = "subareaId")int subareaId,
                                              @RequestParam(value = "pageNum")int pageNum,
                                              @RequestParam(value = "pageSize") int pageSize) {
        //判断该商品是否参加了该展会
        List<Goods> goodsList = exhibitionService.queryAllGoodsByExhibitionId(exhibitionId);
        //判断商品是否符合该分区
        for(Goods goods:goodsList){
            if(goodsJoinExhibitionDao.checkGoodsSubarea(exhibitionId,goods.getGoodsId(),subareaId) == null){
                goodsList.remove(goods);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        return pageInfo;
    }

    /**
     * 即将上线的展会
     */
    @ApiOperation(value = "返回即将上线的展会信息", notes = "直接调用即可")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryReadyToStartExhibitionInfo/{pageNum}")
    public ResponseJson<Map<String,Object>> readyToStartExhibitionInfo(@PathVariable int pageNum) {
        Map<String,Object>map = exhibitionService.queryReadyToStartExhibitionInfo(pageNum);
        if(map.get("info")=="页数错误"){
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }else{
            return new ResponseJson(true, map);
        }
    }

    /**
     *  根据展会的id查询展会的具体信息
     */
    @ApiOperation(value = "根据展会的id查询展会的具体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会的id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/ExhibitionDetails/id")
    public ExhibitionNew queryExhibitionDetailById(int exhibitionId) {
        return exhibitionService.queryExhibitionDetailById(exhibitionId);
    }

    /**
     *返回展会页面四个进行中的展会信息，用于展会页展示
     */
    @ApiOperation(value = "返回展会页面四个进行中的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryOngoingExhibitionInfo/{pageNum}")
    public  ResponseJson<Map<String,Object>> queryOngoingExhibitionInfo(@PathVariable int pageNum) {
        Map<String,Object>map = exhibitionService.queryOngoingExhibitionInfo(pageNum);
        if(map.get("info")=="页数错误"){
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }else{
            return new ResponseJson(true, map);
        }
    }


}