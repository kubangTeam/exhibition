package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UIDGenerator;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ManagerDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.OrganizerInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImp implements IAccountService {
    @Autowired
    private OrganizerInformation organizerInformation;

    @Autowired
    private OrganizerInformationDao organizerInformationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Manager manager;

    @Autowired
    private ManagerDao managerDao;


    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private Company company;

    /**判断账号是否为承办方账号
     *
     * @param userId
     * @return
     */
    @Override
    public int isOrganizerOrNot(int userId) {
        organizerInformation=organizerInformationDao.GetOrganizerInfoFromId(userId);
        int organizerId = organizerInformation.getId();
        if( organizerId!=0){
            return organizerId;
        }else{
            return 0;
        }

    }

    /**
     *
     * @return
     */
    @Override
    public int registerByPhoneNumber() {
        return 0;
    }

    @Override
    public int  registerFromEmail(String email, String password,String RecCode) {
        int recCode = userDao.AddUserPoint(RecCode);
        if(recCode==0){
            return 001;
        }else if(recCode==1)
        {
           String userReccode = UIDGenerator.getUUID();
           int i = userDao.UserRegisterFromEmail(email,password,userReccode);
           if(i==1)return 002;
        }else
            return 003;
        return 004;
    }

    /**判断账号是否为公司账号
     *
     * @param userId
     * @return
     */
    @Override
    public int isCompanyOrNot(int userId) {
        User user = userDao.GetUserInfoFromId(userId);
        int userCompanyId = user.getUserCompanyId();
        if(userCompanyId !=0){
            return userCompanyId;
        }else{
            return 0;
        }
    }

    @Override
    public String identifyUser(int userId) {
        User user = userDao.GetUserInfoFromId(userId);
        Integer companyId = user.getUserCompanyId();
        company = companyDao.selectCompanyInformationById(companyId);
        int companyStatus = company.getIdentifyStatus();

        Integer organizerId =  user.getUserOrganizerId();
        organizerInformation = organizerInformationDao.GetOrganizerInfoFromId(organizerId);
        int organizerStatus = organizerInformation.getIdentifyStatus();

        if(companyId != null && organizerId ==null){
            if(companyStatus ==2)
                return "商家";
            else return  "商家，未处于认证状态";
        }else if(companyId == null && organizerId !=null){
            if(organizerStatus ==2)
                return "承办方";
            else return  "承办方，未处于认证状态";
        }else if(companyId == null && organizerId ==null){
            return "个人用户";
        }else
            return("用户状态异身份异常");
    }

    @Override
    public String identifyAdmin(String account) {
        manager = managerDao.checkAdminByAccount(account);
        if(manager.getPassword()!=null)
            return "管理员";
        else
            return "非管理员账号或管理员账号错误";
    }



}
