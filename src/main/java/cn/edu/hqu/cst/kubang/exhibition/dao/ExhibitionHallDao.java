package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionHall;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExhibitionHallDao {
    ExhibitionHall selectExhibitionHallByName(String exhibitionName);
    ExhibitionHall selectExhibitionHallById(int id);
}
