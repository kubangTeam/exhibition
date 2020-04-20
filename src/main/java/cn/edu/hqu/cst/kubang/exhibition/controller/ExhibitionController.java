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
    @PostMapping("/seller/add")
    @ApiOperation(value = "商家添加一个展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibition", value = "展会对象", required = true, dataType = "Exhibition", paramType = "body")
    })
    public Map<String, String> sellerAdd(@RequestBody Exhibition exhibition) {
        String value = "";
        String code = "";
        if (exhibitionService.saveExhibition(exhibition) > 0) {
            value = "添加成功";
            code = "005";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //商家查询我的公司的的展会(不包括已删除) 如果想要细查某个状态的展会，交给前端处理
    @ApiOperation(value = "商家查询自己所属公司的的展会", notes = "如果想要细查某个状态的展会，交给前端处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/seller/query/companyExhibitions/{userId}/{pageNum}")
    public PageInfo<Exhibition> sellerQueryCompanyExhibitions(@PathVariable int userId, @PathVariable int pageNum) {
        return exhibitionService.queryAllExhibitionsByUserId(userId, pageNum);
    }

    /**
     * 根据id查询展会详情
     * @param id
     * @return
     */
    @ApiOperation(value = "id查询展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/id/{id}")
    public Exhibition adminQueryById(@PathVariable int id) {
        return exhibitionDao.queryExhibitionByID(id);
    }

    //管理员根据关键词查询所有的展会（除了删除的）
    @ApiOperation(value = "管理员根据关键词查询所有的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/admin/query/keyWord")
    public PageInfo<Exhibition> adminQueryKeyWord(String keyWord, int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByKeyWord(keyWord, pageNum);
        return pageInfo;
    }

    //管理员根据状态查询所有的展会,不显示已删除
    @ApiOperation(value = "管理员根据状态查询所有的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/{status}/{pageNum}")
    public PageInfo<Exhibition> adminQueryByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatus(status, pageNum);
        return pageInfo;
    }

    //管理员查询所有状态的展会,不显示已删除
    @ApiOperation(value = "管理员查询所有状态的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/allStatus/{pageNum}")
    public PageInfo<Exhibition> adminQueryAllStatus(@PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryAllExhibitions(pageNum);
        return pageInfo;
    }

    //管理员修改展会状态，不可修改已删除的展会，因为他看不到
    @ApiOperation(value = "管理员修改展会状态", notes = "不可修改已删除的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "query")
    })
    @PutMapping("/admin/update/status")
    public Map<String, String> adminUpdateStatus(int id, int userId, int status) {
        String value;
        String code;
        if (exhibitionDao.queryExhibitionByID(id).getStatus() == 4) {
            value = "找不到该展会";
            code = "016";
        } else {
            int i = exhibitionService.modifyExhibitionStatus(id, userId, status);
            if (i == -1) {
                value = "无权限";
                code = "004";
            } else if (i == 1) {
                value = "修改成功";
                code = "005";
            } else {
                value = "系统异常";
                code = "-001";
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //用户查询所有展会，一定是通过审核（status=2）的
    @ApiOperation(value = "用户查询所有展会", notes = "只返回通过审核（status=2）的展会数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/user/query/all/{pageNum}")
    public PageInfo<Exhibition> userQueryAll(@PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatus(2, pageNum);
        return pageInfo;
    }

    //管理员修改展会全部信息但不可修改状态,修改状态有专门方法 或 商家修改状态为0展会信息
    @ApiOperation(value = "管理员修改展会全部信息 或 商家修改状态为0展会信息", notes = "不可修改状态,修改状态有专门方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibition", value = "展会对象", required = true, dataType = "Exhibition", paramType = "body"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/server/update/all")
    public Map<String, String> serverUpdateAll(@RequestBody Exhibition exhibition, @RequestParam int userId) {
        String value;
        String code;
        int i = exhibitionService.modifyExhibition(exhibition, userId);
        if (i == -1) {
            value = "存在空参数";
            code = "024";
        } else if (i == 1) {
            value = "修改成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //管理员删除 或 商家删除状态为0的展会
    @ApiOperation(value = "管理员删除 或 商家删除状态为0的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path")
    })
    @DeleteMapping("/server/delete/id/{id}/{userId}")
    public Map<String, String> serverDeleteById(@PathVariable int id, @PathVariable int userId) {
        String value;
        String code;
        int i = exhibitionService.deleteExhibition(id, userId);
        if (i == -1) {
            value = "无权限";
            code = "004";
        } else if (i == 1) {
            value = "删除成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //所有用户根据关键词查询所有的展会（已通过审核）
    @ApiOperation(value = "所有用户根据关键词查询所有的展会", notes = "已通过审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/all/query/keyWord")
    public PageInfo<Exhibition> allQueryKeyWord(String keyWord, int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatusAndKeyWord(keyWord, pageNum, 2);
        return pageInfo;
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
    @GetMapping("/queryGoodsByExhibitionId/{id}")
    public PageInfo<Goods> allGoodByKeyWord(@RequestParam(value = "exhibitionId")int exhibitionId, int pageNum) {
        //查询出展会id对应的的商家id列表
        List companyIdList = new ArrayList<>();
        List<CompanyJoinExhibition> companyJoinExhibitionList = new ArrayList<>();
        companyJoinExhibitionList =  companyJoinExhibitionDao.selectCompanyIdByExhibitionId(exhibitionId);
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
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/querySubareaGoodsByExhibitionId/{id}")
    public PageInfo<Goods> allSubareaGoodById(@RequestParam(value = "exhibitionId")int exhibitionId,
                                            @RequestParam(value = "subareaId")int subareaId,
                                            int pageNum) {
        //查询出展会id对应的的商家id列表
        List companyIdList = new ArrayList<>();
        List<CompanyJoinExhibition> companyJoinExhibitionList = new ArrayList<>();
        companyJoinExhibitionList =  companyJoinExhibitionDao.selectCompanyIdByExhibitionId(exhibitionId);
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




}