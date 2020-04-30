package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UploadFile;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserIntegral;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserIntegralDTO;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 13:45
 * @description:
 **/

@RestController
@RequestMapping("/user/information")
@Api(tags = "用户资料管理")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;
    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload.user}")
    private String uploadPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;


    /**
     * @Date: 2020.04.27 14:36
     * @Description: 获取用户资料，用户登录
     */
    @ApiOperation(value = "获取用户资料，用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/login/in")
    public ResponseJson<User> LoginIn(String account, String password) {
        return userInfoService.getUserInfo(account, password);
    }

    /**
     * @Date: 2020.04.27 14:20
     * @Description: 用户修改昵称
     */
    @ApiOperation(value = "用户修改名字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "待修改的名字", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/name")
    public ResponseJson changeName(Integer userId, String userName) {
        return userInfoService.changeName(userId, userName);
    }

    /**
     * @Date: 2020.04.27 14:20
     * @Description: 用户修改、绑定邮箱
     */
    /*@ApiOperation(value = "用户修改、绑定邮箱",notes = "返回200表示成功，500表示失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "待绑定邮箱", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/email")
    public ResponseJson changeEmail(Integer userId,String email) {
        return userInfoService.changeEmail(userId,email);
    }*/

    /**
     * @Date: 2020.04.27 14:20
     * @Description: 用户修改密码
     */
    @ApiOperation(value = "用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "待修改的密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "oldPassword", value = "输入的旧的密码", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/password")
    public ResponseJson changePass(Integer userId, String newPassword, String oldPassword) {
        return userInfoService.changePass(userId, newPassword, oldPassword);
    }

    /**
     * @Date: 2020.04.27 22:04
     * @Author：SunChonggao
     * @Description: 上传用户头像
     */
    @ApiOperation(value = "上传用户头像", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "头像图片", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/profilePicture", method = RequestMethod.POST)
    public Map<String, String> uploadPicture(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "userId") int userId) throws IOException {
        String value;
        String code;
        if (file.isEmpty()) {
            value = "未选择文件";
            code = "021";
        } else {
            String webPath = domain + contextPath + "/images/user/";
            String photo = UploadFile.uploadFile(uploadPath, webPath, file);
            userInfoService.changePhoto(userId, photo);
            value = "上传成功";
            code = "005";

        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    /**
     * @Date: 2020.04.29 13:07
     * @Description: 查询用户积分历史
     */
    @ApiOperation(value = "查询用户积分历史", notes = "返回005（成功）024（参数不合法）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/integral/history")
    public ResponseJson<List<UserIntegralDTO>> queryUserIntegralHistory(Integer id) {
        return userInfoService.queryUserIntegral(id);
    }

    /**
     * @Date: 2020.04.30 17:29
     * @Description:
     *   忘记密码
     */
    @ApiOperation(value = "忘记密码", notes = "返回005（成功）,024（参数不合法）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/password/reset")
    public ResponseJson resetPassword(Integer id, String code, String newPassword, HttpServletRequest request) {
        return userInfoService.resetPassword(id,code,newPassword,request);
    }
}
