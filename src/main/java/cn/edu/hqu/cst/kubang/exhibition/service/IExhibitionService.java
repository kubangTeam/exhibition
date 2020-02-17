package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;

import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 22:24
 *  @Description:
 */

public interface IExhibitionService {
    //根据 id查询
    Exhibition queryExhibitionByID(Integer id);
    //根据 状态查询 默认一次显示5页，每页10个
    List<Exhibition> queryExhibitionByStatus(Integer status);
    //根据 关键词查询 默认一次显示5页，每页10个
    List<Exhibition> queryExhibitionByKeyWord(String keyWord);
    // 新增
    int saveExhibition(Exhibition exhibition);
    // 修改
    int modifyExhibition(Exhibition exhibition);
    // 根据id删除
    int deleteExhibition(Integer id);
}