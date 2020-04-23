package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/collect")
@Api(tags = "用户收藏")
public class UserCollectionController {

    @Resource
    private IUserCollectService userCollectService;

    /**
     *  用户增加收藏展品
     */
    @ApiOperation(value = "用户增加收藏展品",notes = "返回200表示成功，500表示失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsId", value = "展品的id", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/goods")
    public Integer addCollectGoods(int userId,int goodsId) {
        return userCollectService.addCollectGoods(userId,goodsId);
    }

    /**
     * 用户增加收藏公司
     */
    @ApiOperation(value = "用户增加收藏公司",notes = "返回200表示成功，500表示失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "公司的id", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/company")
    public Integer addCollectCompany(int userId,int companyId) {
        return userCollectService.addCollectCompany(userId,companyId);
    }

    /**
     * 用户查看收藏展品
     */
    @ApiOperation(value = "用户查看收藏展品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/goods")
    public List<GoodsNewDto> queryCollectGoods(int userId) {
        return userCollectService.queryCollectGoods(userId);
    }

    /**
     * 用户查看收藏公司
     */
    @ApiOperation(value = "用户查看收藏公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/company")
    public List<Map<String,Object>> queryCollectCompany(int userId) {
        return userCollectService.queryCollectCompany(userId);
    }
}
