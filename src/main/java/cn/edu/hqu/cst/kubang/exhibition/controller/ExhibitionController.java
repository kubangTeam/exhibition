package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class ExhibitionController implements Constants {

    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;

    @Autowired
    private IExhibitionService exhibitionService;
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
    public ResponseJson<Map<String,Object>> subareaInformation(@RequestParam(value = "exhibitionId")int exhibitionId) {
        Map<String, Object> map = new HashMap<>();
        map = exhibitionService.querySubareaByExhibitionId(exhibitionId);
        if(map.get("info")=="查询成功"){
            return new ResponseJson(true, map);
        }else{
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }
    }


    /**
     * 根据展会id推荐四个商品
     * @return
     */
    @ApiOperation(value = " 根据展会Id推荐四个商品", notes = "重新请求实现换一批，没有在展商品(-008)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会Id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryFourGoodsByExhibitionId")
    public ResponseJson<Map<String,Object>> queryGoodsByExhibitionId(@RequestParam(value = "exhibitionId")int exhibitionId) {
        List<Goods> list = exhibitionService.queryGoodsByExhibitionId(exhibitionId);
        if(list.isEmpty())
            return new ResponseJson(false, "-008","没有在展商品",null);
        else{
            List<Goods> result = this.getRandomNumList(COUNT_RECOMMEND_2,list);
            return new ResponseJson(true,"005","操作成功",result);
        }
    }


    /**
     * 根据展会id查询展会id和二级分类id查询商品
     * @return
     */
    @ApiOperation(value = "根据展会id查询展会id和二级分类id查询商品", notes = "展会Id+分区Id(每一页固定8个数据)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "subareaId", value = "分区id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/querySubareaGoodsByExhibitionId")
    public  ResponseJson<Map<String,Object>> allSubareaGoodById(@RequestParam(value = "exhibitionId")int exhibitionId,
                                              @RequestParam(value = "subareaId")int subareaId,
                                              @RequestParam(value = "pageNum")int pageNum) {
        Map<String,Object>map = exhibitionService.queryGoodsByExhibitionIdAndSubareaId(exhibitionId,subareaId,pageNum);
        if(map.get("info")=="页数错误"){
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }else{
            return new ResponseJson(true, map);
        }

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
            //@ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryOngoingExhibitionInfo")
    public  ResponseJson<List<ExhibitionNew>> queryOngoingExhibitionInfo() {
        List list = exhibitionService.getExhibitionIdInRedis();
        List<ExhibitionNew> result= new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            ExhibitionNew exhibitionNew = exhibitionService.queryExhibitionDetailById(Integer.parseInt(list.get(i).toString()));
            result.add(exhibitionNew);
        }
        return new ResponseJson(true,"005","操作成功",result);
    }
    @ApiOperation(value = "展会页面推荐展品",notes = "1/2/3/4分别对应第1/2/3/4张卡片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "第几个卡片", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/recommend/{num}")
    public ResponseJson<List<Goods>> recommendGoods(@PathVariable int num){
        List list = exhibitionService.getExhibitionIdInRedis();
        System.out.println(list);
        List<Goods> goodsList = exhibitionService.queryGoodsByExhibitionId(Integer.parseInt(list.get(num-1).toString()));
        if(list.isEmpty())
            return new ResponseJson(false, "-008","没有在展商品",null);
        else{
            List<Goods> result = this.getRandomNumList(COUNT_RECOMMEND_2,goodsList);
            return new ResponseJson(true,"005","操作成功",result);
        }
    }

    private <T> List<T> getRandomNumList(int nums, List<T> list) {
        List<T> result = new ArrayList<>();
        List temp = new ArrayList<>();
        Random r = new Random();
        while(result.size() < nums){
            int num = r.nextInt(list.size());
            if(!temp.contains(num)) {
                result.add(list.get(num));
                temp.add(num);
            }
        }
        return result;
    }






}