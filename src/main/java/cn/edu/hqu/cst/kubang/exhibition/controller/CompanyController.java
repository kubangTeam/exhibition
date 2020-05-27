package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import cn.edu.hqu.cst.kubang.exhibition.service.UserService;
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
import cn.edu.hqu.cst.kubang.exhibition.Utilities.UploadFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunquan
 * @Date: 2020.04.15
 * @Description:商家相关功能 商家功能分为4个部分：
 * 1、申请广告：商品、横幅
 * 2、参加展会：
 * 3、查询功能：关键字查询、状态查询自己的商品
 * 4、管理功能：
 * 1、对商品、广告进行修改、删除、增加
 * 2、个人资料管理
 * <p>
 * 1、/identify 公司认证
 * 2、/getInformation 获取公司资料
 *   /getInformationByCompanyId 根据商家id查询公司资料
 * 3、/updateInformation 修改商家资料
 * 4、/queryAttendedExhibition/{userId}/{pageNum} 商家查询自己公司的参加过的展会
 *
 *
 */
@RestController
@RequestMapping("/company")
@Api(tags = "商家功能")
public class CompanyController {

    @Autowired
    ICompanyService companyService;

    @Autowired
    Company company;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    private UserService userService;

    @Autowired
    private IExhibitionService exhibitionService;

    @Value("${pagehelper.pageSize1}")
    private int pageSize1;//一页显示8个

    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload.company}")
    private String uploadPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @ApiOperation(value = "公司认证", notes = "前端需要传送的参数：用户ID、公司名称、地址、网址、公司类别、简介、营业执照（照片）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "name", value = "企业名称", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "address", value = "企业地址", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "website", value = "企业网站", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "企业类型", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "tel", value = "企业电话", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "introduce", value = "企业简介", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "file", value = "企业营业执照", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping("/identify")
    /**
     * 公司认证功能：前端需要传送的参数：用户ID、公司名称、地址、网址、公司类别、电话、简介、营业执照
     *
     */
    public ResponseJson<Map<String,Object>> CompanyIdentify(@RequestParam(value = "userId") int userId,
                                               @RequestParam(value = "name") String name,
                                               @RequestParam(value = "address") String address,
                                               @RequestParam(value = "website") String website,
                                               @RequestParam(value = "type") String type,
                                               @RequestParam(value = "tel") String tel,
                                               @RequestParam(value = "introduce") String introduce,
                                               @RequestParam(value = "file") MultipartFile file
                                               ) throws IOException {

        String value = null;
        String code = null;

        String webPath = domain + contextPath + "/images/company/";
        String pic = UploadFile.uploadFile(uploadPath, webPath, file);
        Map<String, Object> map = new HashMap<>();

        map = companyService.CompanyIdentify(userId, name, address, website, type, tel, introduce, pic);
        if(map.get("msg")=="公司认证信息上传成功"){
            return new ResponseJson(true, map);
        }else{
            return new ResponseJson(false, map);
        }
    }

    @ApiOperation(value = "商家查询自己的资料", notes = "前端需要传送的参数：商家ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/getInformationByCompanyId")
    /**
     * 获取公司资料：前端根据商家Id查询商家资料
     */
    public Map<String, Object> getCompanyInformationById(@RequestParam(value = "id") int id) {
        Map<String, Object> map = new HashMap<>();
        //判断该账号是否认证为商家账号
        company = companyDao.selectCompanyInformationById(id);
        if(company !=null){
            map.put("companyInformation", company);
        }else{
            String value = "该商家id不存在";
            map.put("response", value);
        }
        return map;
    }


    /**
     * 修改商家资料，需要固定能够修改的字段
     * @param
     * @return
     */
    @ApiOperation(value = "修改商家资料",notes = "前端需要传送的参数：商家类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "商家ID", required = true, dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "name", value = "企业名称", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "address", value = "企业地址", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "website", value = "企业网站", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "企业类型", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "tel", value = "企业电话", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "introduce", value = "企业简介", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "file", value = "头像(可选)", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping("/updateInformation")
    /**
     * 编辑公司资料
     */
    public ResponseJson<Map<String,Object>> updateCompanyInformation(@RequestParam(value = "companyId") int companyId,
                                                                     @RequestParam(value = "name") String name,
                                                                     @RequestParam(value = "address") String address,
                                                                     @RequestParam(value = "website") String website,
                                                                     @RequestParam(value = "type") String type,
                                                                     @RequestParam(value = "tel") String tel,
                                                                     @RequestParam(value = "introduce") String introduce,
                                                                     @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {

        String webPath = null;
        String pic =null;
        if(file!=null){
            webPath = domain + contextPath + "/images/company/";
            pic = UploadFile.uploadFile(uploadPath, webPath, file);
        }else{
            pic = companyDao.selectCompanyInformationById(companyId).getHeadPicture();
            System.out.println(pic);
        }
        Map<String,Object>map =companyService.CompanyInfoUpdate(companyId,name,address,website,type,tel,introduce,pic);
        if(map.get("info")=="修改成功"){
            return new ResponseJson(true, map);
        }else{
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }
    }


    //商家查询我的公司的的展会
    @ApiOperation(value = "商家查询自己公司的参加过的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页(8个每一页)", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryAttendedExhibition/{userId}/{pageNum}")
    public ResponseJson<Map<String,Object>> sellerQueryCompanyExhibitions(@PathVariable int userId, @PathVariable int pageNum) {

        Map<String, Object> map = new HashMap<>();
        map = companyService.queryCompanyAttendedExhibition(userId,pageNum);
        if(map.get("info")=="页数错误"){
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }else{
            return new ResponseJson(true, map);
        }
    }

    @ApiOperation(value = "商家参加展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "商家id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/attendExhibition")
    public ResponseJson<Map<String,Object>> attendExhibition(@RequestParam("companyId") int companyId,@RequestParam("exhibitionId") int exhibitionId)
    {
        Map<String,Object> result =exhibitionService.companyAttendExhibition(exhibitionId,companyId);

        if(result.get("info") == "申请参展失败")
            return new ResponseJson(false,ResponseCodeEnums.BAD_REQUEST);
        else
            return new ResponseJson(true,result);
    }
}
