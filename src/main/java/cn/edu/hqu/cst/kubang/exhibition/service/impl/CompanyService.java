package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyJoinExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.CompanyRepository;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.ExhibitionRepository;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AccountServiceImp accountServiceImp;

    @Autowired
    CompanyJoinExhibition companyJoinExhibition;

    @Autowired
    CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    ExhibitionDao exhibitionDao;

    @Autowired
    Exhibition exhibition;

    @Autowired
    CompanyRepository companyRepository;


    @Override
    public String CompanyIdentify(int userId, String name, String address,
                                String website, String type, String introduce,
                                String tel, String headPicture) {
        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        company.setWebsite(website);
        company.setType(type);
        company.setIntroduction(introduce);
        company.setTelephone(tel);
        company.setHeadPicture(headPicture);
        //通过用户id获取用户的电话 头像
        User user = userDao.GetUserInfoFromId(userId);
        String msg = "";
        if(user != null) {
            Company _company = companyDao.selectCompanyInformationById(user.getUserCompanyId());
            if (_company != null) {
                if (_company.getIdentifyStatus() == 0) {
                    msg = "该账号信息本地保存，等待正式上传";
                } else if (_company.getIdentifyStatus() == 1) {
                    msg = "该账号正在等待审核";
                } else if (_company.getIdentifyStatus() == 2) {
                    msg = "该账号已通过审核";
                } else if (_company.getIdentifyStatus() == 3) {
                    msg = "该账号审核未通过";
                } else if (_company.getIdentifyStatus() == 4) {
                    msg = "该账号已删除";
                } else {
                    msg = "认证状态字段错误";
                }
            } else {
                //公司认证状态 1：上传成功，等待审核
                company.setIdentifyStatus(1);
                //将个人信息表的公司字段填上公司信息表的主键
                if (companyDao.addUnidentifiedCompanyInfo(company) > 0) {
                    if (userDao.setCompanyId(userId, company.getId()) > 0) {
                        msg = "公司认证信息上传成功";
                        //将公司信息保存到ES服务器
                        companyRepository.save(company);
                    }
                } else {
                    msg = "公司认证信息上传失败";
                }
            }
        }
        else {
            msg = "无此用户";
        }
        return msg;
    }

    //查看公司所参加的展会
    @Override
    public List<Exhibition> queryCompanyAttendedExhibition(int userId) {
        int companyId = accountServiceImp.isCompanyOrNot(userId);
        List<CompanyJoinExhibition> companyJoinExhibitionList =companyJoinExhibitionDao.selectExhibitionByCompanyId(companyId);
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
