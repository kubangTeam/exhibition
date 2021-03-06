package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyNew;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CompanyDao {
    /**
     * 公司信息表相关操作
     * @param company
     * @return
     */
    List<Company> selectAll();//查询所有展会，用于搜索
    int addUnidentifiedCompanyInfo(Company company);
    //根据状态查询商家信息
    List<Company> getCompaniesByIdentifyStatus(int identifyStatus);
    int identifyCompany(int id);
    Company selectCompanyInformationById(int id);//根据商家id查询商家资料
    int updateCompanyInformation(Company company);//编辑商家的资料
    int delete(int id);//删除数据，测试用
    // 根据categoryId查找对应的名字
    String queryCompanyNameById(Integer id);


    //根据状态查询商家信息
//    List<CompanyNew> getCompanyInfoByIdentifyStatus(int identifyStatus);
//    CompanyNew selectCompanyInfoById(int id);//根据商家id查询商家资料
//    int updateCompanyInformation(CompanyNew companyNew);//编辑商家的资料

}
