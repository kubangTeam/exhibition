package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.City;
import cn.edu.hqu.cst.kubang.exhibition.entity.Province;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionHallService;
import cn.edu.hqu.cst.kubang.exhibition.service.ILocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 19:11
 * @description: 位置服务
 **/

@RestController
@RequestMapping("/user/location")
@Api(tags = "位置服务")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IExhibitionHallService iExhibitionHallService;

    /**
     * @Date: 2020.04.27 19:28
     * @Description:
     *   获取省份信息列表
     */
    @ApiOperation(value = "获取省份信息列表",notes = "不包括港澳台")
    @GetMapping("/province")
    public List<Province> getAllProvince() {
        return locationService.getAllProvince();
    }


    /**
     * @Date: 2020.04.27 19:29
     * @Description:
     *   获取城市列表
     */
    @ApiOperation(value = "获取城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "provinceCode", value = "省份的编码", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/city")
    public List<City> getAllCities(Integer provinceCode) {

        return locationService.getAllCities(provinceCode);
    }

    /**
     * @Date: 2020.04.27 19:29
     * @Description:sunquan
     *   根据展会的展厅信息查询该展会所在的城市
     */
    @ApiOperation(value = "获取城市名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionHallId", value = "展会所在展厅的编码", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/cityName")
    public Map<String,String> getAllCityName(Integer exhibitionHallId) {
        return iExhibitionHallService.findCityNameByExhibitionHallId(exhibitionHallId);
    }



}
