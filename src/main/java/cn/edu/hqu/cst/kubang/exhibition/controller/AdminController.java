package cn.edu.hqu.cst.kubang.exhibition.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunquan
 * @Date: 2020.04.15
 * @Description:管理员相关功能
 * 管理员功能分为4个部分：
 * 1、审核商品、商家、展会（包括展会前期申请，展会总体申请）的申请
 * 2、审核广告的申请：横幅、商品广告
 * 3、查询功能：关键字查询、状态查询
 * 4、管理功能：对商家、商品、展会信息、广告进行修改、删除
 */
@RestController
@RequestMapping("/Admin")
@Api(tags = "管理员功能")
public class AdminController {


}
