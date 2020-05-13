package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Account")
@Api(tags = "账户管理功能")
public class AccountController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    //管理员根据状态查询所有的展会
    @ApiOperation(value = "判断账号的权限信息",notes = "参数为用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "请求第几页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/checkPermission")
    public Map<String, Object> checkPermission(@RequestParam("userId") int userId) {
        Map<String, Object>result = new HashMap<>();
        Map<String,Object>map1 = accountServiceImp.isCompanyOrNot(userId);
        Map<String,Object>map2 = accountServiceImp.isOrganizerOrNot(userId);
        Integer companyId = (Integer) map1.get("companyId");
        Integer organizerId = (Integer) map2.get("organizerId");
        if(organizerId==null || organizerId==0){
            return map1;
        }else{
            if(companyId!=0){
                result.put("info","错误的权限");
                return result;
            }else {
                return map2;
            }
        }
    }




}
