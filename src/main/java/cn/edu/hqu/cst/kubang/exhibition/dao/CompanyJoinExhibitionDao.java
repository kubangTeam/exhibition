package cn.edu.hqu.cst.kubang.exhibition.dao;


import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyJoinExhibitionDao {
    List<CompanyJoinExhibition> selectCompanyIdByExhibitionId(int exhibitionId);//根据展会id查询所有参加该展会的商家id

    //添加一个CompanyJoinExhibition类
    int insertCompanyJoinExhibition(CompanyJoinExhibition companyJoinExhibition);//添加展品

    //删除数据 测试用
    int delete(int id);

}
