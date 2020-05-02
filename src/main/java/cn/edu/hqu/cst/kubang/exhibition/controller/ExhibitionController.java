package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.*;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

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
 * 1、查看自己的展会信息。
 * 2、申请广告
 * 3、
 * 根据人群划分划分路径和方法名
 * 买家buyer 卖家(商家)seller 管理员admin 买家与卖家(用户)user 卖家与管理员(服务者)server 买家卖家管理员(所有人)all
 * 此外还增加了超级管理员superAdmin 它唯一职责就是可以对已删除的展会进行操作,暂时还不写
 *
 *
 * 接口简介
 * 1、/queryExhibitionSubareaById/{id} 根据展会id查询展会分区信息
 * 2、/queryGoodsByExhibitionId/{id} 据展会id查询展会的所有商品
 * 3、/querySubareaGoodsByExhibitionId/{id} 根据展会id和分区id查询展会分区商品信息
 * 4、/queryReadyToStartExhibitionInfo 返回即将上线的展会信息
 *
 */
@RestController
@RequestMapping("/exhibition")
@Api(tags = "展会方相关功能")
public class ExhibitionController {

    IExhibitionService exhibitionService;


    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;




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
        if(subInformation!=null){
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
    @ApiOperation(value = " 根据展会id查询展会的所有商品", notes = "点击分类页的“更多”进入展会页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryGoodsByExhibitionId")
    public PageInfo<Goods> allGoodByKeyWord(@RequestParam(value = "exhibitionId")int exhibitionId,
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

        List<Goods> goodsList = exhibitionService.queryAllGoodsByExhibitionId(exhibitionId);
        for(Goods goods:goodsList){
            if(goodsJoinExhibitionDao.checkGoodsSubarea(exhibitionId,goods.getGoodsId(),subareaId) != 1){
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
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryReadyToStartExhibitionInfo")
    public PageInfo<Exhibition> readyToStartExhibitionInfo(@RequestParam(value = "pageNum")int pageNum) {
        List<Exhibition> exhibitionList =exhibitionService.queryReadyToStartExhibitionInfo();
        PageHelper.startPage(pageNum, pageSize2);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
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



}