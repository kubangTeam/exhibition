package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;

import java.util.List;
import java.util.Map;

public interface ICompanyService {
    //公司认证
    Map<String, Object> CompanyIdentify(int userId, String name, String address,
                                         String website, String type, String tel, String introduce, String HeadPicture);

    List<Exhibition>queryCompanyAttendedExhibition(int userId);
    List<Company> queryAll();
}
