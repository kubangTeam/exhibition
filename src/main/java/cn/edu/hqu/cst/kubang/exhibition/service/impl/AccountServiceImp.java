package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ManagerDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.OrganizerInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Manager;
import cn.edu.hqu.cst.kubang.exhibition.entity.OrganizerInformation;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImp implements IAccountService {
    @Autowired
    private OrganizerInformation organizerInformation;

    @Autowired
    private OrganizerInformationDao organizerInformationDao;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserInformationDao userInformationDao;

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

    /**判断账号是否为公司账号
     *
     * @param userId
     * @return
     */
    @Override
    public int isCompanyOrNot(int userId) {
        userInformation = userInformationDao.GetUserInfoFromId(userId);
        int userCompanyId = userInformation.getUserCompanyId();
        if(userCompanyId !=0){
            return userCompanyId;
        }else{
            return 0;
        }
    }

    @Override
    public String identifyUser(int userId) {
        userInformation = userInformationDao.GetUserInfoFromId(userId);
        Integer companyId = userInformation.getUserCompanyId();
        company = companyDao.selectCompanyInformationById(companyId);
        int companyStatus = company.getIdentifyStatus();

        Integer organizerId =  userInformation.getUserOrganizerId();
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
