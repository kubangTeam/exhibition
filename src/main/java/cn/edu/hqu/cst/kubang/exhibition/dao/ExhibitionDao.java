package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    //查询所有
    List<Exhibition> queryAllExhibitions();
    //根据 id查询
    Exhibition queryExhibitionByID(Integer id);
    //根据 状态查询所有
    List<Exhibition> queryExhibitionsByStatus(Integer status);
    //根据 关键词查询所有
    List<Exhibition> queryExhibitionsByKeyWord(String[] keyWords);
    // 新增 保存修改 都是status = 0
    int saveExhibition(Exhibition exhibition);
    // 修改展会状态 比如点击上传把status改为1
    int modifyExhibitionStatus(@Param("id")Integer id, @Param("status")Integer status);
    // 修改
    int modifyExhibition(Exhibition exhibition);
    // 根据id删除
    int deleteExhibition(Integer id);
    // 根据公司id查找它的展会id
    List<Integer> queryExhibitionByCompanyId(Integer companyId);
    // 根据用户id查找公司的展品
    List<Exhibition> queryExhibitionsByUserId(Integer userId);
}
