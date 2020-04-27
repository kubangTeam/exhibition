package cn.edu.hqu.cst.kubang.exhibition.dao;


import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExhibitionSubareaDao {
    //根据展会id查询展会的二级分类
    List<ExhibitionSubarea> selectByExhibitionId(int exhibitionId);

    int insertExhibitionSubareaInfo(ExhibitionSubarea exhibitionSubarea);

    int deleteExhibitionSubareaInfoByExhibitionId(int exhibitionId);//删除测试数据，测试用

}
