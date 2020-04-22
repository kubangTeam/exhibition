package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;

import java.util.List;

public interface ICompanyService {
    //公司认证
    int CompanyIdentify(int userId,String name,String address,
                         String website,String type, String introduce, String HeadPicture);

    List<Exhibition>queryCompanyAttendedExhibition(int userId);
}
