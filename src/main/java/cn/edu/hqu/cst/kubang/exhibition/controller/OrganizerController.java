package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UploadFile;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import cn.edu.hqu.cst.kubang.exhibition.service.ElasticsearchService;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.ExhibitionServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: sunquan
 * @Date: 2020.04.23
 * @Description:举办方相关功能
 *
 * /个人信息模块
 * 1、/identifyAsOrganizer  承办方账号认证
 *
 * /提交模块
 * 1、/holdExhibition 举办一个展会，提交展会信息
 *
 * /审核模块
 * 1、/checkOrganizerHoldExhibition 查看承办方举办的展会列表展
 * 2、/checkCompanyApplyByExhibitionId 查看审核参加自己举办的展会的商家申请
 * 3、/verifyCompanyApplyByExhibitionId 审核通过参加自己举办的展会的某商家的申请。
 *
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

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload.organizer}")
    private String uploadPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;


    @ApiOperation(value = "承办方账号认证", notes = "传入的参数为承办方的认证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID(通过注册后返回个人信息取得用户id)", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "organizer", value = "承办方名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "codeCertificateType", value = "代码证类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "codeCertificateId", value = "代码证代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "codeCertificatePic", value = "代码证照片", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonName", value = "负责人姓名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonIdcard", value = "负责人身份证号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonpic1", value = "身份证手持照片", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonpic2", value = "身份证手持人像面", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "responsiblePersonTel", value = "负责人手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headPicure", value = "负责人头像", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/identifyAsOrganizer")
    public Map<String, String> registerAsOrganizer(@RequestParam(value = "userId") int userId,
                                                   @RequestParam(value = "organizer") String organizer,
                                                   @RequestParam(value = "codeCertificateType") String codeCertificateType,
                                                   @RequestParam(value = "codeCertificateId") String codeCertificateId,
                                                   @RequestParam(value = "codeCertificatePic") MultipartFile codeCertificatePic,
                                                   @RequestParam(value = "responsiblePersonName") String responsiblePersonName,
                                                   @RequestParam(value = "responsiblePersonIdcard") String responsiblePersonIdcard,
                                                   @RequestParam(value = "responsiblePersonpic1") MultipartFile responsiblePersonpic1,
                                                   @RequestParam(value = "responsiblePersonpic2") MultipartFile responsiblePersonpic2,
                                                   @RequestParam(value = "responsiblePersonTel") String responsiblePersonTel,
                                                   @RequestParam(value = "headPicure") MultipartFile headPicure) throws IOException {
        String value = null;
        String code = null;
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    @ApiOperation(value = "展会举办方举办一个展会", notes = "日期格式：yyyy-MM-dd，日期格式错误(-008)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "举办方账号ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "展会名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "exhibitionHallId", value = "展馆id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "session", value = "举办届数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "period", value = "举办周期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "introduce", value = "展会简介", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "icon", value = "展会图标url", required = true, dataType = "String", paramType = "query"),
            //@ApiImplicitParam(name = "subAreaList", value = "展会分区信息数据集", required = true, allowMultiple = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/holdExhibition")
    public ResponseJson<Map<String, Object>> holdExhibition(@RequestParam(value = "userId") int userId,
                                                            @RequestParam(value = "name") String name,
                                                            @RequestParam(value = "startTime") String startTime,
                                                            @RequestParam(value = "endTime") String endTime,
                                                            @RequestParam(value = "exhibitionHallId") int exhibitionHallId,
                                                            @RequestParam(value = "session") int session,
                                                            @RequestParam(value = "period") String period,
                                                            @RequestParam(value = "introduce") String introduce,
                                                            @RequestParam(value = "subAreaList") @ApiParam(value = "展会分区信息数据集") List<String> subAreaList,
                                                            @RequestParam(value = "icon") String pic
    ) {

        String value = null;

        Map<String, String> map = new HashMap<>();
        if (accountServiceImp.identifyUser(userId) == "承办方") {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = new Date();
            Date endDate = new Date();
            try {
                startDate = dateFormat.parse(startTime);
                endDate = dateFormat.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
                return new ResponseJson(false,"-008","日期格式错误");
            }
            int exhibitionId = exhibitionService.holdExhibition(userId, name, startDate, endDate, exhibitionHallId, session, period, introduce, pic);
            int result = exhibitionService.addSubareaInfo(subAreaList, exhibitionId);
            if (exhibitionId != 0 && result == 1) {
                value = "上传展会成功";
            }
            map.put("response", value);
            return new ResponseJson(true, map);
        } else {
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "查看承办方举办的展会列表展", notes = "传入的参数为承办方id，返回展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "举办方ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/queryOrganizerHoldExhibition")
    public Map<String, Object> queryOrganizerHoldExhibition(@RequestParam(value = "id") int userId) throws IOException {
        String value = null;
        String code = null;
        Map<String, Object> map = new HashMap<>();

        List<Exhibition> exhibitions = exhibitionService.queryAllExhibitionInfoByOrganizerID(userId);

        map.put("count", exhibitions.size());
        map.put("data", exhibitions);
        return map;
    }


    @ApiOperation(value = "查看审核参加自己举办的展会的商家申请", notes = "传入的参数为展会id，返回参加该展会的商家信息以及参展商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/checkCompanyApplyByExhibitionId")
    public ResponseJson<List<Company>> checkCompanyApplyByExhibitionId(@RequestParam(value = "exhibitionId") int exhibitionId) throws IOException {
        List<Company> result =   exhibitionService.getUnverifiedCompaniesByExhibitionId(exhibitionId);
        return new ResponseJson(true,result);
    }

    @ApiOperation(value = "审核通过参加自己举办的展会的某商家的申请", notes = "传入的参数为商家id，以及展会id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "举办方ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "exhibitionId", value = "举办方ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/verifyCompanyApplyByExhibitionId")
    public ResponseJson<String> verifyCompanyApplyByExhibitionId(@RequestParam(value = "companyId") int companyId,@RequestParam("exhibitionId") int exhibitionId) throws IOException {
        exhibitionService.verifyCompanyApplyByExhibitionId(companyId,exhibitionId);
       return new ResponseJson(true,"审核成功");
    }
}
