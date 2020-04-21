package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.OrganizerInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.OrganizerInformation;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImp implements IAccountService {
    @Autowired
    private OrganizerInformation organizerInformation;

    @Autowired
    private OrganizerInformationDao organizerInformationDao;

    @Autowired
    UserInformation userInformation;

    @Autowired
    UserInformationDao userInformationDao;

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





}
