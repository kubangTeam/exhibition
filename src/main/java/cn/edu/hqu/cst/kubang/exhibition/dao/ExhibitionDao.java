package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionNew;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
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

    //根据 id查询
    ExhibitionNew queryExhibitionDetailsByID(Integer id);

    //根据状态查询所有
    List<Exhibition> queryExhibitionsByStatus(Integer status);

    //根据 关键词查询所有
    List<Exhibition> queryExhibitionsByKeyWord(String[] keyWord);

    // 新增 保存修改
    int saveExhibition(Exhibition exhibition);

    // 修改展会状态 比如点击上传把status改为1
    int modifyExhibitionStatus(Integer id,Integer status);

    // 修改
    int modifyExhibition(Exhibition exhibition);

    int deleteById(int id);//删除测试数据，测试用

    // 根据展会id查找它的轮播图
    List<String> queryExbitionPicById(int exhibitionId);
}
