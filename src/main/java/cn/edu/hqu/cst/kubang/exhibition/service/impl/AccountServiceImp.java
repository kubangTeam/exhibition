package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.OrganizerInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.OrganizerInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImp implements IAccountService {
    @Autowired
    private OrganizerInformation organizerInformation;

    @Autowired
    private OrganizerInformationDao organizerInformationDao;
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

}
