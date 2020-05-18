package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionNew;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 22:24
 *  @Description:
 */

public interface IExhibitionService {

    /**
     *  承办方功能
     */
    //添加一个展会
    int holdExhibition(int userId, String name, Date startTime, Date endTime,
                       int exhibitionHallId,int session,String period,
                       String introduce, String picPath);
    //添加展会分区信息
    int addSubareaInfo(List<String> subAreaList,int exhibitionId);

    //管理员查询所有展会
    List<Exhibition> queryAllExhibitionInfoByAdmin();
    //举办方查询所有展会
    List<Exhibition> queryAllExhibitionInfoByOrganizerID(int organizerID);

    //若为承办方查询所有其举办的展会 若为个人用户查询自己参加过的展会（注释掉了）
    //List<Exhibition>queryExhibitionInfoByUserId(int userId);

    //用户查询他参加过的展会?
    List<Exhibition> queryExhibitionInfoByUser(int userID);

    //根据账号id和展会状态查询展会信息 若为管理员则对所有展会按照状态查询 若为承办方 则按照承办方的所举办的展会的状态来查询
    List<Exhibition>queryExhibitionInfoByUserIdAndStatus(int userId,int status);


    Map<String,Object>queryReadyToStartExhibitionInfo(int pageNum);
    ExhibitionNew queryExhibitionDetailById(int exhibitionId);
    List<Exhibition> queryAll();
    List<Goods> queryRandomGoodsByExhibitionId(int exhibitionId);
    List<Exhibition> queryOnGoing();
    Map<String ,Object>queryGoodsByExhibitionIdAndSubareaId(int exhibitionId,int subAreaId,int pageNum);

    Map<String ,Object>querySubareaByExhibitionId(int exhibitionId);
    List getExhibitionIdInRedis();
    boolean updateExhibitionInRedis() throws Exception;
    void addExhibitionIntoRedis(int id);



}