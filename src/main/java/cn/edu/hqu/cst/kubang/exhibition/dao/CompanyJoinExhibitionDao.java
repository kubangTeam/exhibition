package cn.edu.hqu.cst.kubang.exhibition.dao;


import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyJoinExhibitionDao {

    int insertCompanyJoinExhibition(CompanyJoinExhibition companyJoinExhibition);
    int deleteSingleCompanyAttend(int id);
    int deleteAllAttend(int exhibitionId);
    List<CompanyJoinExhibition>selectCompanyByExhibitionId(int exhibitionId);
    List<CompanyJoinExhibition>selectExhibitionByCompanyId(int companyId);
    List<CompanyJoinExhibition>selectAll();
}
