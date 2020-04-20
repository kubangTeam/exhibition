package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.CompanyService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserInformationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import cn.edu.hqu.cst.kubang.exhibition.Utilities.upload;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunquan
 * @Date: 2020.04.15
 * @Description:商家相关功能
 * 商家功能分为4个部分：
 * 1、申请广告：商品、横幅
 * 2、参加展会：
 * 3、查询功能：关键字查询、状态查询自己的商品
 * 4、管理功能：
 *      1、对商品、广告进行修改、删除、增加
 *      2、个人资料管理
 */
@Controller
@RequestMapping("/company")
@Api(tags = "商家功能")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    Company company;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    UserInformationServiceImpl userInformationService;

    @ApiOperation(value = "公司认证",notes = "前端需要传送的参数：用户ID、公司名称、地址、网址、公司类别、简介、营业执照（照片）")
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
    public Map<String,String> CompanyIdentify(@RequestParam(value = "userId") int userId,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "address") String address,
                              @RequestParam(value = "website") String website,
                              @RequestParam(value = "type") String type,
                              @RequestParam(value = "introduce") String introduce,
                              @RequestParam(value = "file") MultipartFile file,
                              HttpServletRequest request) throws IOException {

        String value = null;
        String code = null;
        String path = request.getServletContext().getRealPath("/codeCertificatePic");
        if(file.isEmpty()){
            value = "未选择文件";
            code = "021";
        }
        else{
            String pic = upload.uploadFile(path,file);
            int status = companyService.CompanyIdentify(userId,name,address,website,type,introduce,pic);
            if(status ==1){
                value = "上传成功";
                code = "005";
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }


    @ApiOperation(value = "商家查询自己的资料",notes = "前端需要传送的参数：用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/getInformation")
    /**
     * 获取公司资料：前端根据账号Id查询商家资料
     */
    public Map<String,Object> getCompanyInformation(@RequestParam(value = "id") int id){
        Map<String,Object> map = new HashMap<>();
        //判断该账号是否认证为商家账号
        int companyId = userInformationService.isCompanyOrNot(id);
        if(companyId!=0){
            company = companyDao.selectCompanyInformationById(companyId);
            String value = "该用户已通过商家认证";
            map.put("response",value);
            map.put("companyInformation",company);
        }else{
            String value = "该用户未通过商家认证";
            map.put("response",value);
        }
        return map;
    }

    @ApiOperation(value = "修改商家资料",notes = "前端需要传送的参数：商家类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/updateInformation")
    /**
     * 编辑公司资料
     */
    public String updateCompanyInformation(@RequestBody Company company){
        if(companyDao.updateCompanyInformation(company) ==1){
            return "修改成功";
        }else{
            return "修改失败";
        }

    }

}
