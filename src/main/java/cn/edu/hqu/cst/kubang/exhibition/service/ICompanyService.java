package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;

public interface ICompanyService {
    //公司认证
    int CompanyIdentify(int userId,String name,String address,
                         String website,String type, String introduce, String HeadPicture);


}
