package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService implements ICompanyService {
    @Autowired
    Company company;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    UserInformationDao userInformationDao;

    @Autowired
    UserInformation userInformation;

    @Autowired
    AccountServiceImp accountServiceImp;

    @Autowired
    CompanyJoinExhibition companyJoinExhibition;

    @Autowired
    ExhibitionDao exhibitionDao;

    @Autowired
    Exhibition exhibition;

    @Override
    public int CompanyIdentify(int userId, String name, String address,
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
        company.setIdentifyStatus(1);

        //保存商家用户
        //报错提醒
        companyDao.addUnidentifiedCompanyInfo(company);
        return(1);
    }

    //查看公司所参加的展会
    @Override
    public List<Exhibition> queryCompanyAttendedExhibition(int userId) {
        int companyId = accountServiceImp.isCompanyOrNot(userId);
        List<CompanyJoinExhibition> companyJoinExhibitionList = companyDao.selectExhibitionIdByCompanyId(companyId);
        List exhibitionId = new ArrayList();
        List exbitionList = new ArrayList<Exhibition>();

        for(int i  = 0;i<companyJoinExhibitionList.size();i++){
            CompanyJoinExhibition temp = companyJoinExhibitionList.get(i);
            exhibition = exhibitionDao.queryExhibitionByID(temp.getExhibitionId());
            exhibitionId.add(i,temp.getExhibitionId());
            exbitionList.add(i,exhibition);
        }

        return exbitionList;

    }


}
