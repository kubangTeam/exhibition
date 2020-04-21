package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.*;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
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
 * 1、/holdExhibition 展会举办方举办一个展会
 * 2、/queryExhibitionSubareaById/{id} 根据展会id查询展会分区信息
 * 3、/queryGoodsByExhibitionId/{id} 据展会id查询展会的所有商品
 * 4、/querySubareaGoodsByExhibitionId/{id} 根据展会id和分区id查询展会分区商品信息
 * 5、/queryReadyToStartExhibitionInfo 返回即将上线的展会信息
 */
@RestController
@RequestMapping("/exhibition")
@Api(tags = "展会方相关功能")
public class ExhibitionController {

    @Autowired
    private IExhibitionService exhibitionService;

    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    private CompanyJoinExhibition companyJoinExhibition;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个

    /**
     * 添加一个展会
     * @param exhibition
     * @return
     */
    @PostMapping("/holdExhibition")
    @ApiOperation(value = "展会举办方举办一个展会",notes = "提交展会信息字段：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "企业名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "企业地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "website", value = "企业网站", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "企业类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "企业简介", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "企业营业执照", required = true, dataType = "MultipartFile", paramType = "query")
    })
    public Map<String, String> sellerAdd(@RequestBody Exhibition exhibition) {
        String value = "";
        String code = "";
        if (exhibitionDao.saveExhibition(exhibition) > 0) {
            value = "添加成功";
            code = "005";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    /**
     * 根据展会id返回展会二级分类信息
     */
    @ApiOperation(value = "根据展会id查询展会分区信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryExhibitionSubareaById/{id}")
    public List<ExhibitionSubarea> subareaInformation(@RequestParam(value = "exhibitionId")int exhibitionId) {
        List<ExhibitionSubarea> subInformation = new ArrayList<ExhibitionSubarea>();
        subInformation = exhibitionSubareaDao.selectByExhibitionId(exhibitionId);
        return subInformation;
    }

    /**
     * 根据展会id查询展会的所有商品
     * @return
     */
    @ApiOperation(value = "展会一级商品推荐", notes = "据展会id查询展会的所有商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryGoodsByExhibitionId/{id}/{pageNum}")
    public PageInfo<Goods> allGoodByKeyWord(@RequestParam(value = "exhibitionId")int exhibitionId,@RequestParam(value = "pageNum") int pageNum) {
        //查询出展会id对应的的商家id列表
        List companyIdList = new ArrayList<>();
        List<CompanyJoinExhibition> companyJoinExhibitionList = new ArrayList<>();
        companyJoinExhibitionList = exhibitionDao.selectCompanyIdByExhibitionId(exhibitionId);
        for(int i =0;i<companyJoinExhibitionList.size();i++){
            int companyId  = companyJoinExhibitionList.get(i).getCompanyId();
            companyIdList.add(i,companyId);
        }
        /**
         * 1、根据商家列表id查询对应商家的商品id列表
         * 2、在商品id列表中查找参加了该展会的商品
         */
        List<Goods> goodsList = new ArrayList<>();
        List<Goods> temp = new ArrayList<>();
        int index = 0;
        for(int j =0;j<companyIdList.size();j++){
            int companyId = (int) companyIdList.get(j);
            temp = goodsDao.selectGoodsByCompanyId(companyId,2);
            for(Goods goods:temp){
                if(goodsJoinExhibitionDao.checkGoodsJoinOrNot(exhibitionId,goods.getGoodsId()) == 1){
                    goodsList.add(index,goods);
                    index++;
                }
            }
        }
        PageHelper.startPage(pageNum, pageSize2);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        return pageInfo;

    }

    /**
     * 根据展会id查询展会id和二级分类id查询商品
     * @return
     */
    @ApiOperation(value = "展会一级商品推荐", notes = "据展会id查询展会的所有商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "subareaId", value = "分区id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/querySubareaGoodsByExhibitionId/{id}/{pageNum}")
    public PageInfo<Goods> allSubareaGoodById(@RequestParam(value = "exhibitionId")int exhibitionId,
                                                @RequestParam(value = "subareaId")int subareaId,
                                                 @RequestParam(value = "pageNum")int pageNum) {
        //查询出展会id对应的的商家id列表
        List companyIdList = new ArrayList<>();
        List<CompanyJoinExhibition> companyJoinExhibitionList = new ArrayList<>();
        companyJoinExhibitionList =  exhibitionDao.selectCompanyIdByExhibitionId(exhibitionId);
        for(int i =0;i<companyJoinExhibitionList.size();i++){
            int companyId  = companyJoinExhibitionList.get(i).getCompanyId();
            companyIdList.add(i,companyId);
        }
        /**
         * 1、根据商家列表id查询对应商家的商品id列表
         * 2、在商品id列表中查找参加了该展会的商品 且符合传来的二级分区id
         */
        List<Goods> goodsList = new ArrayList<>();
        List<Goods> temp = new ArrayList<>();
        int index = 0;
        for(int j =0;j<companyIdList.size();j++){
            int companyId = (int) companyIdList.get(j);
            temp = goodsDao.selectGoodsByCompanyId(companyId,2);
            for(Goods goods:temp){
                if(goodsJoinExhibitionDao.checkGoodsSubarea(exhibitionId,goods.getGoodsId(),subareaId) == 1){
                    goodsList.add(index,goods);
                    index++;
                }
            }
        }
        PageHelper.startPage(pageNum, pageSize2);
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

}