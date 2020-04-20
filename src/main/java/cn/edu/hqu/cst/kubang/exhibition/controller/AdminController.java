package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
 *
 */
@RestController
@RequestMapping("/Admin")
@Api(tags = "管理员功能")
public class AdminController {
    @Autowired
    private Exhibition exhibition;

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Value("${pagehelper.pageSize1}")
    private int pageSize1;//一页显示8个

    //管理员根据状态查询所有的展会
    @ApiOperation(value = "返回所有状态的展会",notes = "通过页数查询固定长度（pageSize1）的展会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "path"),
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
    @GetMapping("/queryExhibition/{status}/{pageNum}")
    public PageInfo<Exhibition> adminQueryExhibitionByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize1);
        List<Exhibition> exhibitionList  = exhibitionDao.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

}
