package cn.edu.hqu.cst.kubang.exhibition.dao;


import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExhibitionSubareaDao {
    //根据展会id查询展会的二级分类
    List<ExhibitionSubarea> selectByExhibitionId(int exhibitionId);

}