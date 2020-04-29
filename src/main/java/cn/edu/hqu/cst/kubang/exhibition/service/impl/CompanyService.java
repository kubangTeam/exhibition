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
    public String CompanyIdentify(int userId, String name, String address,
                                String website, String type, String introduce,
                                String HeadPicture) {
        company.setName(name);
        company.setAddress(address);
        company.setWebsite(website);
        company.setType(type);
        company.setIntroduction(introduce);
        //通过用户id获取用户的电话 头像
        userInformation = userInformationDao.GetUserInfoFromId(userId);
        company = companyDao.selectCompanyInformationById(userInformation.getUserCompanyId());

        if(userInformation.getUserCompanyId()!=null){
            if(company.getIdentifyStatus()==0) {
                return "该账号信息本地保存，等待正式上传";
            }else if(company.getIdentifyStatus()==1){
            return "该账号正在等待审核";
            }else if(company.getIdentifyStatus()==2){
                return "该账号已通过审核";
            }else if(company.getIdentifyStatus()==3){
                return "该账号审核未通过";
            }else if(company.getIdentifyStatus()==4){
                return "该账号已删除";
            }else{
                return "认证状态字段错误";
            }
        }
        else{
            String userPhone = userInformation.getUserAccount();
            String headPicture = userInformation.getUserPicture();
            company.setTelephone(userPhone);
            company.setHeadPicture(headPicture);
            //公司认证状态 1：上传成功，等待审核
            company.setIdentifyStatus(1);
            //将个人信息表的公司字段填上公司信息表的主键
            int result = userInformationDao.setCompanyId(userId,company.getId());
            if(companyDao.addUnidentifiedCompanyInfo(company)==1 && result ==1){
                return "公司认证信息上传成功";
            }else{
                return "公司认证信息上传失败";
            }
        }
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
            //查询到了展会就添加进去，为查询到展会就跳过继续查询
            if(exhibition!=null) {
                exhibitionId.add(i, temp.getExhibitionId());
                exbitionList.add(i, exhibition);
            }else continue;
        }
        return exbitionList;

    }
    @Override
    //查询所有公司,用于搜索
    public List<Company> queryAll(){
        return companyDao.selectAll();
    }


}
