package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.OrganizerInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 展会举办方相关DAO
 */
@Mapper
@Repository
public interface OrganizerInformationDao {
    OrganizerInformation GetOrganizerInfoFromId(int id);



}
