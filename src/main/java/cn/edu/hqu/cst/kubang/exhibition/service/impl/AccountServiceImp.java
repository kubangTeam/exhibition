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

import java.util.HashMap;
import java.util.Map;
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
    private Company company;

    @Autowired
    private CompanyDao companyDao;




    @Override
    public Map<String,Object> isOrganizerOrNot(int userId) {
        Map<String,Object> map = new HashMap<>();
        String msg = null;
        User user = userDao.GetUserInfoFromId(userId);
        Integer userOrganizerId = user.getUserOrganizerId();
        if(user.getUserOrganizerId()==null ||user.getUserOrganizerId()==0){
            msg = "个人用户";
        }else{
            if(organizerInformationDao.GetOrganizerInfoFromId(userOrganizerId)!=null){
                organizerInformation = organizerInformationDao.GetOrganizerInfoFromId(userOrganizerId);
                int status = organizerInformation.getIdentifyStatus();
                if (status == 0) {
                    msg = "该账号信息本地保存，等待正式上传";
                } else if (status == 1) {
                    msg = "该账号正在等待审核";
                } else if (status == 2) {
                    msg = "该账号已通过审核";
                } else if (status == 3) {
                    msg = "该账号审核未通过";
                } else if (status == 4) {
                    msg = "该账号已删除";
                } else {
                    msg = "认证状态字段错误";
                }
            }else{
                msg = "承办方信息不存在";
            }
        }
        map.put("info",msg);
        map.put("organizerId",userOrganizerId);
        return map;
    }
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
    public Map<String,Object> isCompanyOrNot(int userId) {

        Map<String,Object> map = new HashMap<>();
        String msg = null;
        User user = userDao.GetUserInfoFromId(userId);
        int userCompanyId = user.getUserCompanyId();
        if(userCompanyId!=0){
            if(companyDao.selectCompanyInformationById(userCompanyId)!=null){
                company = companyDao.selectCompanyInformationById(userCompanyId);
                int status = company.getIdentifyStatus();
                if (status == 0) {
                    msg = "该账号信息本地保存，等待正式上传";
                } else if (status == 1) {
                    msg = "该账号正在等待审核";
                } else if (status == 2) {
                    msg = "该账号已通过审核";
                } else if (status == 3) {
                    msg = "该账号审核未通过";
                } else if (status == 4) {
                    msg = "该账号已删除";
                } else {
                    msg = "认证状态字段错误";
                }
            }else{
                msg = "公司信息不存在";
            }
        }else{
            msg = "个人用户";
        }
        map.put("info",msg);
        map.put("companyId",userCompanyId);
        return map;
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
    public Map<String,Object>identifyAdmin(String account) {
        Map<String,Object> map = new HashMap<>();
        String msg = null;
        manager = managerDao.checkAdminByAccount(account);
        if(manager.getPassword()!=null)
            msg = "管理员";
        else
            msg = "非管理员账号或管理员账号错误";
        map.put("info",msg);
        return  map;
    }
}
