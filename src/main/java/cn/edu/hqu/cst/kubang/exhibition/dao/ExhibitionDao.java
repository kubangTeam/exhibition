package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 22:23
 *  @Description:
 */
@Mapper
@Repository
public interface ExhibitionDao {
    //根据 id查询
    Exhibition queryExhibitionByID(Integer id);
    //根据 状态查询
    List<Exhibition> queryExhibitionByStatus(Integer status);
    //根据 关键词查询
    List<Exhibition> queryExhibitionByKeyWord(String keyWord);
    // 新增
    int saveExhibition(Exhibition exhibition);
    // 修改 管理员功能
    int modifyExhibition(Exhibition exhibition);
    // 根据id删除
    int deleteExhibition(Integer id);
}
