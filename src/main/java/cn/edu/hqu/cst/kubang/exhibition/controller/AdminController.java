package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UploadFile;
import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: sunquan
 * @Date: 2020.04.15
 * @Description:管理员相关功能
 * 管理员功能分为4个部分：
 * 1、审核商品、商家、展会（包括展会前期申请，展会总体申请）的申请
 * 2、审核广告的申请：横幅、商品广告
 * 3、查询功能：关键字查询、状态查询
 * 4、管理功能：对商家、商品、展会信息、广告进行修改、删除
 *
 *  接口简介
 *  1、/queryAllExhibition/{pageNum}  通过页数查询固定长度（pageSize1）的展会信息
 *  2、/queryExhibition/{status}/{pageNum}  通过展会状态、页数查询固定长度（pageSize1）的展会信息
 *  3、/queryExhibitionByKeyWord/{keyword}/{pageNum} 通过关键字、页数查询固定长度（pageSize1）的展会信息
 *  4、/updateExhibitionInfo 修改展会信息
 *
 *
 *  审核模块
 *  1、/updateExhibitionStatus 修改展会状态
 */
@RestController
@RequestMapping("/Admin")
@Api(tags = "管理员功能")
public class AdminController {
    @Autowired
    private Exhibition exhibition;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private Company company;
    @Value("${pagehelper.pageSize1}")
    private int pageSize1;//一页显示10

    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8

    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload.goods}")
    private String uploadPathGoods;
    @Value("${exhibition.path.upload.organizer}")
    private String uploadPathOrganizer;
    @Value("${exhibition.path.upload.company}")
    private String uploadPathCompany;
    @Value("${exhibition.path.upload.advertisement}")
    private String uploadPathAdvertisement;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    //管理员根据状态查询所有的展会
    @ApiOperation(value = "返回所有状态的展会",notes = "通过页数查询固定长度（pageSize1=10）的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryAllExhibition/{pageNum}")
    public PageInfo<Exhibition> adminQueryAllExhibition(@PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Exhibition> exhibitionList  = exhibitionDao.queryAllExhibitions();
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //管理员根据状态查询所有的展会
    @ApiOperation(value = "管理员根据状态查询所有的展会",notes = "通过展会状态、页数查询固定长度（pageSize1）的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryExhibitionByStatus/{status}/{pageNum}")
    public PageInfo<Exhibition> adminQueryExhibitionByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Exhibition> exhibitionList  = exhibitionDao.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //管理员用过关键字查询所有展会
    @ApiOperation(value = "管理员根据关键词查询所有的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键词(支持关键词数组查询)", required = true, dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryExhibitionByKeyWord")
    public PageInfo<Exhibition> adminQueryExhibitionByKeyWords(@RequestParam("keyWord") String[] keyWord, @RequestParam("pageNum")int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByKeyWord(keyWord);
        PageInfo<Exhibition> pageInfo =new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //管理员修改展会信息（不可修改状态,修改状态有专门方法）
    @ApiOperation(value = "管理员修改展会全部信息", notes = "不可修改状态,修改状态有专门方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibition", value = "展会对象", required = true, dataType = "Exhibition", paramType = "body")
    })
    @PutMapping("/updateExhibitionInfo")
    public Map<String, String> serverUpdateAll(@RequestBody Exhibition exhibition) {
        String value;
        String code;
        if (exhibitionDao.queryExhibitionByID(exhibition.getId()) == null) {
            value = "找不到该展会";
            code = "016";
        }else{
            int i = exhibitionDao.modifyExhibition(exhibition);
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
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }


    //管理员修改展会状态，不可修改已删除的展会，因为他看不到
    @ApiOperation(value = "管理员修改展会状态", notes = "不可修改已删除的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "query")
    })
    @PutMapping("/updateExhibitionStatus")
    public Map<String, String> adminUpdateStatus(int id,int status) {
        String value;
        String code;

        if (exhibitionDao.queryExhibitionByID(id) == null) {
            value = "找不到该展会";
            code = "016";
        } else {
            if(exhibitionDao.queryExhibitionByID(id).getStatus() == 4){
                value = "展会处于删除状态";
                code = "016";
            }else {
                int i = exhibitionDao.modifyExhibitionStatus(id, status);
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
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }




    //管理员根据状态查询所有的展会
    @ApiOperation(value = "管理员根据状态查询所有的公司",notes = "通过公司状态、页数查询固定长度（pageSize2）的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "公司的状态", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryCompanyByStatus/{status}/{pageNum}")
    public PageInfo<Company> adminQueryCompanyByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Company> companyList  = companyDao.getCompaniesByIdentifyStatus(status);
        PageInfo<Company> pageInfo = new PageInfo<>(companyList);
        return pageInfo;
    }


    //管理员根据状态查询所有的展会
    @ApiOperation(value = "管理员查询所有的公司",notes = "通过页数查询固定长度（pageSize2）的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryAllCompany/{pageNum}")
    public PageInfo<Company> adminQueryAllCompany(@PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Company> companyList  = companyDao.selectAll();
        PageInfo<Company> pageInfo = new PageInfo<>(companyList);
        return pageInfo;
    }

    //管理员根据状态查询所有的展会
    @ApiOperation(value = "根据状态查询所有的商品",notes = "通过商品状态、页数查询长度为10的商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "商品的状态(可选)", required = false, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    //@RequestMapping(value ={"/queryGoodsByStatus/{status}/{pageNum}","/queryGoodsByStatus/{status}/{pageNum}"})
    @GetMapping({"/queryGoodsByStatus/{pageNum}/{status}","/queryGoodsByStatus/{pageNum}"})
    public PageInfo<Goods> adminQueryGoodsByStatus(@PathVariable Optional<Integer> status,
                                                   @PathVariable int pageNum) {

        if(status.isPresent()){
            PageHelper.startPage(pageNum, pageSize1);
            PageInfo<Goods>pageInfo   = goodsService.queryByGoodsStatus(status.get(),pageNum,pageSize1);
            return pageInfo;
        }else{
            PageHelper.startPage(pageNum, pageSize1);
            List<Goods> goodsList  =goodsService.queryGoodsALl();
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            return pageInfo;
        }
    }
    @ApiOperation(value = "统一上传展品图片", notes = "未选择文件（021）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "展品图片", required = true,  dataType = "file", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/upload/picture/{type}", method = RequestMethod.POST)
    public ResponseJson<String> uploadPicture(@RequestParam(value = "file") MultipartFile file,
                                              @PathVariable(value = "type") int flag)
            throws IOException {
        if (file.isEmpty()) {
            return new ResponseJson(false, ResponseCodeEnums.No_FileSELECT);
        } else {
            String url = null, webPath = domain + contextPath;
            switch (flag){
                case 1:
                    webPath += "/images/organizer";
                    url = UploadFile.uploadFile(uploadPathOrganizer, webPath, file);
                    break;
                case 2:
                    webPath += "/images/company";
                    url = UploadFile.uploadFile(uploadPathOrganizer, webPath, file);
                    break;
                case 3:
                    webPath += "/images/goods";
                    url = UploadFile.uploadFile(uploadPathGoods, webPath, file);
                    break;
            }
            return new ResponseJson(true,url);
        }
    }






}
