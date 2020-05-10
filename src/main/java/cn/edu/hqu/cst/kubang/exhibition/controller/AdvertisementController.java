package cn.edu.hqu.cst.kubang.exhibition.controller;

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

}
