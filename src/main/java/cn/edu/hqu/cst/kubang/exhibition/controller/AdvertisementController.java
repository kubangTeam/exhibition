package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.AdvertisementDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AdvertisementServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author: sunquan
 * @Date: 2020.04.15
 * @Description:向前端返回广告条幅页
 */
@RestController
@RequestMapping("/advertisement")
@Api(tags = "广告横幅")
public class AdvertisementController {
    @Autowired
    AdvertisementServiceImpl advertisementService;

    @Autowired
    Advertisement advertisement;

    @Autowired
    AdvertisementDao advertisementDao;

    @ApiOperation(value = "返回推荐的横幅",notes = "前端调用即可，一页8个")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, dataType = "int", paramType = "query")

    })
    @GetMapping("/get")
    /**
     * 获取推荐的广告横幅
     */
    public ResponseJson<Map<String,Object>> getCompanyInformation(@RequestParam(value = "pageNum") int pageNum){
        Map<String,Object>map = advertisementService.recommendAds(pageNum);
        if(map.get("info")=="页数错误"){
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }else{
            return new ResponseJson(true, map);
        }
    }


    //2020/5/27 新增了String 类型的picture字段
    @ApiOperation(value = "修改广告信息（不包括状态）",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "priority", value = "优先级由高到底3 2 1", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "图片地址", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/updateAds")
    public ResponseJson<Map<String,Object>> updateAds(@RequestParam(value = "id") int id,
                                                      @RequestParam(value = "priority") int priority,
                                                      @RequestParam(value = "startTime") String startTime,
                                                      @RequestParam(value = "endTime") String endTime,
                                                      @RequestParam(value = "picture") String picture)
    {
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
        System.out.println(priority);
        advertisement.setId(id);
        advertisement.setPriority(priority);
        advertisement.setStartTime(startDate);
        advertisement.setEndTime(endDate);
        advertisement.setPicture(picture);
        Map<String,Object>map = advertisementService.updateAds(advertisement);
        if(map.get("info")=="修改成功"){
            return new ResponseJson(true, map);
        }else{
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "修改广告状态",notes = "1：已提交 2：通过 3：未通过 4：删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/updateAdsStatus")
    public ResponseJson<Map<String,Object>> updateAdsStatus(@RequestParam(value = "id") int id,
                                                      @RequestParam(value = "status") int status){
        int i = advertisementDao.updateAdsStatus(id,status);
        if(i==1){
            return new ResponseJson(true, "修改成功");
        }else{
            return new ResponseJson(false, ResponseCodeEnums.BAD_REQUEST);
        }
    }
}
