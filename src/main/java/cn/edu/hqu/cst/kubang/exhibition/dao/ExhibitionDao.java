package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.*;
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

    int deleteByStatus(int status);//删除测试数据，测试用

    //根据举办方ID查询展会
    //By Timor 2020/5/18
    List<Exhibition> getExhibitionsByOrganizerID(int organizerID);

    // 根据展会id查找它的轮播图
    List<String> queryExbitionPicById(int exhibitionId);

    //设置前四个在展的展会的优先级
    //By Timor 2020/5/19
    void setOngoingPriority(int exhibitionId, int priority);

    void companyAttendExhibition(int exhibitionId, int companyId);

    void verifyCompanyApplyByExhibitionId(int companyId, int exhibitionId);

    List<Company> getUnverifiedCompaniesByExhibitionId(int exhibitionId);
}
