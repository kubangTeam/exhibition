package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 * 账号服务
 */
public interface IAccountService {

    int isOrganizerOrNot(int userId);

    //用户手机号注册
    int registerByPhoneNumber();

    int isCompanyOrNot(int userId);
}
