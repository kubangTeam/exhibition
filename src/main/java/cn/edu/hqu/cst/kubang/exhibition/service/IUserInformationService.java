package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务 暂无
 * 用户注册
 */
public interface IUserInfromationService {

    //用户手机号注册
    int registerByPhoneNumber();


}
