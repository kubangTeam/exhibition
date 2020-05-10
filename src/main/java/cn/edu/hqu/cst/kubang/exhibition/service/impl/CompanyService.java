package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Pagination;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    Company company;

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

    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个


    @Override
    public Map<String, Object> CompanyIdentify(int userId, String name, String address,
                                               String website, String type, String introduce,
                                               String tel, String headPicture) {
        String msg = null;
        Integer companyId =null;
        User user =null;
        //配置了bean就不能在定义同名对象？？？
        //Company company = null;
        company.setName(name);
        company.setAddress(address);
        company.setWebsite(website);
        company.setType(type);
        company.setIntroduction(introduce);
        company.setTelephone(tel);
        company.setHeadPicture(headPicture);

        user = userDao.GetUserInfoFromId(userId);
        if(user != null) {
            companyId = user.getUserCompanyId();
            if (companyId == null || companyId == 0) {
                //公司认证状态 1：上传成功，等待审核
                company.setIdentifyStatus(1);
                //将个人信息表的公司字段填上公司信息表的主键
                if (companyDao.addUnidentifiedCompanyInfo(company) == 1) {
                    if (userDao.setCompanyId(userId, company.getId()) == 1) {
                        msg = "公司认证信息上传成功";
                        companyId = company.getId();
                        //将公司信息保存到ES服务器
                        companyRepository.save(company);
                    }
                } else
                    msg = "公司认证信息上传失败";
            } else {
                if (companyDao.selectCompanyInformationById(user.getUserCompanyId()) != null) {
                    company = companyDao.selectCompanyInformationById(user.getUserCompanyId());
                    if (company.getIdentifyStatus() == 0) {
                        msg = "该账号信息本地保存，等待正式上传";
                    } else if (company.getIdentifyStatus() == 1) {
                        msg = "该账号正在等待审核";
                    } else if (company.getIdentifyStatus() == 2) {
                        msg = "该账号已通过审核";
                    } else if (company.getIdentifyStatus() == 3) {
                        msg = "该账号审核未通过";
                    } else if (company.getIdentifyStatus() == 4) {
                        msg = "该账号已删除";
                    } else {
                        msg = "认证状态字段错误";
                    }
                }else
                    msg = "用户已提交认证，但公司信息缺失";
            }
        } else msg = "无此用户";

        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("company",companyId);
        return map;
    }

    //查看公司所参加的展会
    @Override
    public Map<String, Object> queryCompanyAttendedExhibition(int userId,int pageNum) {
        Map<String,Object> map = new HashMap<>();
        String info = null;

        int companyId = accountServiceImp.isCompanyOrNot(userId);
        List<CompanyJoinExhibition> companyJoinExhibitionList =null;
        List exhibitionId = new ArrayList();
        List exbitionList = new ArrayList<Exhibition>();

        if(companyJoinExhibitionDao.selectExhibitionByCompanyId(companyId)!=null){
            for(int i  = 0;i<companyJoinExhibitionList.size();i++){
                CompanyJoinExhibition temp = companyJoinExhibitionList.get(i);
                exhibition = exhibitionDao.queryExhibitionByID(temp.getExhibitionId());
                //查询到了展会就添加进去，为查询到展会就跳过继续查询
                if(exhibition!=null) {
                    exhibitionId.add(i, temp.getExhibitionId());
                    exbitionList.add(i, exhibition);
                }else continue;
            }
        }else{
            info = "数据库错误";
            map.put("info",info);
            return map;
        }
        map = Pagination.paginationCompany(pageNum,pageSize2,exbitionList);
        return map;
    }


    @Override
    public Map<String, Object> queryAllCompany(int pageNum) {
        Map<String,Object> map = new HashMap<>();
        String info = null;
        List<Company> companyList = null;
        if(companyDao.selectAll()!=null){
            companyList = companyDao.selectAll();
            map = Pagination.paginationCompany(pageNum,pageSize2,companyList);
        }else{
            info = "数据库错误";
            map.put("info",info);
            return map;
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCompanyByStatus(int pageNum,int status) {
        Map<String,Object> map = new HashMap<>();
        List<Company> companyList = null;
        String info = null;
        if(companyDao.getCompaniesByIdentifyStatus(status)!=null){
            companyList = companyDao.getCompaniesByIdentifyStatus(status);
            map = Pagination.paginationCompany(pageNum,pageSize2,companyList);
        }else{
            info = "数据库错误";
            map.put("info",info);
            return map;
        }
        return map;
    }

    @Override
    public Map<String, Object> CompanyInfoUpdate(int companyId, String name,
                                                 String address, String website, String type,
                                                 String tel, String introduce,
                                                 String HeadPicture) {
        Map<String,Object> map = new HashMap<>();
        String info = null;
        company =null;
        company = companyDao.selectCompanyInformationById(companyId);
        if(company!=null){
            company.setName(name);
            company.setAddress(address);
            company.setWebsite(website);
            company.setType(type);
            company.setTelephone(tel);
            company.setIntroduction(introduce);
            company.setHeadPicture(HeadPicture);
            if(companyDao.updateCompanyInformation(company)==1){
                info = "修改成功";
                //System.out.println(companyDao.selectCompanyInformationById(companyId).getWebsite());
                map.put("info",info);
            }
        }else{
            info = "商家不存在";
            map.put("info",info);
        }
        return map;
    }


}
