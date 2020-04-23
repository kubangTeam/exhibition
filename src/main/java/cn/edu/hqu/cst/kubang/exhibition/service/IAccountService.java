package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 * 账号服务，作为service层的工具类
 */
public interface IAccountService {

    int isOrganizerOrNot(int userId);

    //用户手机号注册
    int registerByPhoneNumber();

    int isCompanyOrNot(int userId);


    // 判断用户身份
    String identifyUser(int userId);
    //判断管理员身份
    String identifyAdmin(String account);
}
