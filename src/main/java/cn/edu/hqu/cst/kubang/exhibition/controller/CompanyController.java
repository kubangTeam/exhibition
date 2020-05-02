package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
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
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "企业名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "企业地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "website", value = "企业网站", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "企业类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "企业简介", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "企业营业执照", required = true, dataType = "MultipartFile", paramType = "query")
    })
    @PostMapping("/identify")
    /**
     * 公司认证功能：前端需要传送的参数：用户ID、公司名称、地址、网址、公司类别、简介、营业执照
     *
     */
    public Map<String, String> CompanyIdentify(@RequestParam(value = "userId") int userId,
                                               @RequestParam(value = "name") String name,
                                               @RequestParam(value = "address") String address,
                                               @RequestParam(value = "website") String website,
                                               @RequestParam(value = "type") String type,
                                               @RequestParam(value = "introduce") String introduce,
                                               @RequestParam(value = "file") MultipartFile file
                                               ) throws IOException {

        String value = null;
        String code = null;
        if (file.isEmpty()) {
            value = "未选择文件";
            code = "021";
        } else {
            String webPath = domain + contextPath + "/images/company/";
            String pic = UploadFile.uploadFile(uploadPath, webPath, file);

            String status = "";
            try{
                status = companyService.CompanyIdentify(userId, name, address, website, type, introduce, pic);
            }
            catch (Exception e){
                System.out.println(e);
            }
            value = status;
            code = "005";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }


    @ApiOperation(value = "商家查询自己的资料", notes = "前端需要传送的参数：用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/getInformation")
    /**
     * 获取公司资料：前端根据账号Id查询商家资料
     */
    public Map<String, Object> getCompanyInformation(@RequestParam(value = "id") int id) {
        Map<String, Object> map = new HashMap<>();
        //判断该账号是否认证为商家账号
        int companyId = userService.isCompanyOrNot(id);
        if (companyId != 0) {
            company = companyDao.selectCompanyInformationById(companyId);
            String value = "该用户已通过商家认证";
            map.put("response", value);
            map.put("companyInformation", company);
        } else {
            String value = "该用户未通过商家认证";
            map.put("response", value);
        }
        return map;
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
     * @param company
     * @return
     */
    @ApiOperation(value = "修改商家资料",notes = "前端需要传送的参数：商家类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/updateInformation")
    /**
     * 编辑公司资料
     */
    public String updateCompanyInformation(@RequestBody Company company) {
        if (companyDao.updateCompanyInformation(company) == 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }


    //商家查询我的公司的的展会
    @ApiOperation(value = "商家查询自己公司的参加过的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/queryAttendedExhibition/{userId}/{pageNum}")
    public PageInfo<Exhibition> sellerQueryCompanyExhibitions(@PathVariable int userId, @PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Exhibition> exhibitionList = companyService.queryCompanyAttendedExhibition(userId);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;

    }
}
