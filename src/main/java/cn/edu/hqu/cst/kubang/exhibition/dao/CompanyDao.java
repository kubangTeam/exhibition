package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;

import java.util.List;

public interface CompanyDao {
    void addUnidentifiedCompanyInfo(String companyName,String type,String address,String webSite,String telephone,String introduction,String codeCertificateType,String codeCertificateId,String codeCertificatePic,String responsiblePersonName,String responsiblePersonIdCard,String headPicture);
    List<Company> getUnidentifiedCompanies();
    void identifyCompany(String companyId);
}
