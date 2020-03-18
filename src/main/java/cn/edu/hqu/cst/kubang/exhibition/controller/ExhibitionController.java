package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 14:09
 * @Description: 根据人群划分划分路径和方法名
 * 买家buyer 卖家(商家)seller 管理员admin 买家与卖家(用户)user 卖家与管理员(服务者)server 买家卖家管理员(所有人)all
 * 此外还增加了超级管理员superAdmin 它唯一职责就是可以对已删除的展会进行操作,暂时还不写
 */
@RestController
@RequestMapping("/exhibition")
@Api(tags = "展会增删改查等服务")
public class ExhibitionController {

    @Autowired
    private IExhibitionService exhibitionService;

    //商家添加一个展会，展会状态为0
    @PostMapping("/seller/add")
    @ApiOperation(value = "商家添加一个展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibition", value = "展会对象", required = true, dataType = "Exhibition", paramType = "body")
    })
    public Map<String, String> sellerAdd(@RequestBody Exhibition exhibition) {
        String value = "";
        String code = "";
        if (exhibitionService.saveExhibition(exhibition) > 0) {
            value = "添加成功";
            code = "005";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //商家查询我的公司的的展会(不包括已删除) 如果想要细查某个状态的展会，交给前端处理
    @ApiOperation(value = "商家查询自己所属公司的的展会", notes = "如果想要细查某个状态的展会，交给前端处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/seller/query/companyExhibitions/{userId}/{pageNum}")
    public PageInfo<Exhibition> sellerQueryCompanyExhibitions(@PathVariable int userId, @PathVariable int pageNum) {
        return exhibitionService.queryAllExhibitionsByUserId(userId, pageNum);
    }

    //管理员根据id查询展会,不会显示已删除（status=4）
    @ApiOperation(value = "管理员根据id查询展会", notes = "不会显示已删除（status=4）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/id/{id}")
    public Exhibition adminQueryById(@PathVariable int id) {
        return exhibitionService.queryExhibitionByID(id);
    }

    //管理员根据关键词查询所有的展会（除了删除的）
    @ApiOperation(value = "管理员根据关键词查询所有的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/admin/query/keyWord")
    public PageInfo<Exhibition> adminQueryKeyWord(String keyWord, int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByKeyWord(keyWord, pageNum);
        return pageInfo;
    }

    //管理员根据状态查询所有的展会,不显示已删除
    @ApiOperation(value = "管理员根据状态查询所有的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/{status}/{pageNum}")
    public PageInfo<Exhibition> adminQueryByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatus(status, pageNum);
        return pageInfo;
    }

    //管理员查询所有状态的展会,不显示已删除
    @ApiOperation(value = "管理员查询所有状态的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/admin/query/allStatus/{pageNum}")
    public PageInfo<Exhibition> adminQueryAllStatus(@PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryAllExhibitions(pageNum);
        return pageInfo;
    }

    //管理员修改展会状态，不可修改已删除的展会，因为他看不到
    @ApiOperation(value = "管理员修改展会状态", notes = "不可修改已删除的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "展会的状态", required = true, dataType = "int", paramType = "query")
    })
    @PutMapping("/admin/update/status")
    public Map<String, String> adminUpdateStatus(int id, int userId, int status) {
        String value;
        String code;
        if (exhibitionService.queryExhibitionByID(id).getStatus() == 4) {
            value = "找不到该展会";
            code = "016";
        } else {
            int i = exhibitionService.modifyExhibitionStatus(id, userId, status);
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
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //用户查询所有展会，一定是通过审核（status=2）的
    @ApiOperation(value = "用户查询所有展会", notes = "只返回通过审核（status=2）的展会数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求第几页", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/user/query/all/{pageNum}")
    public PageInfo<Exhibition> userQueryAll(@PathVariable int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatus(2, pageNum);
        return pageInfo;
    }

    //管理员修改展会全部信息但不可修改状态,修改状态有专门方法 或 商家修改状态为0展会信息
    @ApiOperation(value = "管理员修改展会全部信息 或 商家修改状态为0展会信息", notes = "不可修改状态,修改状态有专门方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibition", value = "展会对象", required = true, dataType = "Exhibition", paramType = "body"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/server/update/all")
    public Map<String, String> serverUpdateAll(@RequestBody Exhibition exhibition, @RequestParam int userId) {
        String value;
        String code;
        int i = exhibitionService.modifyExhibition(exhibition, userId);
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
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //管理员删除 或 商家删除状态为0的展会
    @ApiOperation(value = "管理员删除 或 商家删除状态为0的展会")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "展会id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path")
    })
    @DeleteMapping("/server/delete/id/{id}/{userId}")
    public Map<String, String> serverDeleteById(@PathVariable int id, @PathVariable int userId) {
        String value;
        String code;
        int i = exhibitionService.deleteExhibition(id, userId);
        if (i == -1) {
            value = "无权限";
            code = "004";
        } else if (i == 1) {
            value = "删除成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //所有用户根据关键词查询所有的展会（已通过审核）
    @ApiOperation(value = "所有用户根据关键词查询所有的展会", notes = "已通过审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键词", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/all/query/keyWord")
    public PageInfo<Exhibition> allQueryKeyWord(String keyWord, int pageNum) {
        PageInfo<Exhibition> pageInfo = exhibitionService.queryExhibitionsByStatusAndKeyWord(keyWord, pageNum, 2);
        return pageInfo;
    }
}