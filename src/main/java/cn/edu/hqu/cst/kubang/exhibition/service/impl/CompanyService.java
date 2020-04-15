package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;

public class ComapanyService  implements ICompanyService {
    @Autowired
    Company company;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    UserInformationDao userInformationDao;

    @Autowired
    UserInformation userInformation;

    @Override
    public void CompanyIdentify(int userId, String name, String address,
                                String website, String type, String introduce,
                                String HeadPicture) {
        company.setName(name);
        company.setAddress(address);
        company.setWebsite(website);
        company.setType(type);
        company.setIntroduction(introduce);

        //获取用户的电话 头像
        userInformation = userInformationDao.GetUserInfoFromId(userId);
        String userPhone = userInformation.getUserAccount();
        String headPicture = userInformation.getUserPicture();
        company.setTelephone(userPhone);
        company.setHeadPicture(headPicture);

        //公司认证状态 1：上传成功，等待审核
        company.setIdentifyStatus("1");

        //保存商家用户
        companyDao.

    }

}
