package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 22:24
 *  @Description:
 */

public interface IExhibitionService {
    //查询所有
    List<Exhibition> queryAllExhibitions();
    //根据 id查询
    Exhibition queryExhibitionByID(Integer id);
    //根据 状态查询所有
    List<Exhibition> queryExhibitionsByStatus(Integer status);
    //根据 关键词查询所有
    List<Exhibition> queryExhibitionsByKeyWord(String keyWord);
    //根据状态和关键词查询 比如查找未通过审核的关键词为“1”的所有展品
    List<Exhibition> queryExhibitionsByStatusAndKeyWord(Integer status,String keyWord);
    // 新增
    int saveExhibition(Exhibition exhibition);
    // 修改 1、状态为0用户可以修改 2、状态非0 只有管理员才可以修改
    int modifyExhibition(Exhibition exhibition,Integer userId);
    // 修改展会状态 比如点击上传把status改为1
    int modifyExhibitionStatus(Integer id,Integer userId,Integer status);
    // 根据id修改status=4 不是真正的删除
    int deleteExhibition(Integer id,Integer userId);
}