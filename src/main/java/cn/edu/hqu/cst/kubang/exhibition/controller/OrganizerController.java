package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UploadFile;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.ExhibitionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunquan
 * @Date: 2020.04.23
 * @Description:举办方相关功能
 * /提交模块
 * 1、/holdExhibition 举办一个展会，提交展会信息
 *
 * /审核模块
 * 1、/checkOrganizerHoldExhibition 查看承办方举办的展会列表展
 * 2、/checkCompanyApplyByExhibitionId 审核参加自己举办的展会的商家申请
 */
@RestController
@RequestMapping("/organizer")
@Api(tags = "展会举办方相关功能")
public class OrganizerController {

    @Autowired
    private ExhibitionServiceImpl exhibitionService;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload.organizer}")
    private String uploadPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;



    @ApiOperation(value = "展会举办方举办一个展会",notes = "提交展会信息字段：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "举办方账号ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "展会名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "exhibitionHallId", value = "展馆id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "session", value = "举办届数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "period", value = "举办周期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "展会简介", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "展会图标", required = true, dataType = "MultipartFile", paramType = "query")
    })
    @PostMapping("/holdExhibition")
    public Map<String,String> CompanyIdentify(@RequestParam(value = "id") int userId,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "startTime") Date startTime,
                                              @RequestParam(value = "endTime") Date endTime,
                                              @RequestParam(value = "exhibitionHallId") int exhibitionHallId,
                                              @RequestParam(value = "session") int session,
                                              @RequestParam(value = "period") String period,
                                              @RequestParam(value = "introduce") String introduce,
                                              @RequestParam(value = "picture") MultipartFile file,
                                              HttpServletRequest request) throws IOException {

        String value = null;
        String code = null;
        String path = request.getServletContext().getRealPath("/exhibitionPic");
        if(file.isEmpty()){
            value = "未选择文件";
            code = "021";
        }
        else{
            String webPath = domain + contextPath + "/images/organizer/";
            String pic = UploadFile.uploadFile(path, webPath, file);
            int status =exhibitionService.holdExhibition(userId,name,startTime,endTime,exhibitionHallId,session,period,introduce,pic);
            if(status ==1){
                value = "上传展会成功";
                code = "005";
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    @ApiOperation(value = "查看承办方举办的展会列表展",notes = "传入的参数为承办方id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "举办方ID", required = true, dataType = "int", paramType = "query")

    })
    @PostMapping("/checkOrganizerHoldExhibition")
    public Map<String,String> checkOrganizerHoldExhibition(@RequestParam(value = "id") int userId) throws IOException {
        String value = null;
        String code = null;
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }




}
