package cn.edu.hqu.cst.kubang.exhibition.service;

import java.util.Map;

/**
 * 账号服务，作为service层的工具类
 */
public interface IAccountService {

    Map<String,Object> isOrganizerOrNot(int userId);

    //用户手机号注册
    int registerByPhoneNumber();

    int registerFromEmail(String email, String password,String RecCode);

    Map<String,Object> isCompanyOrNot(int userId);


    // 判断用户身份
    String identifyUser(int userId);
    //判断管理员身份
    Map<String,Object> identifyAdmin(String account);
}
