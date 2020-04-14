package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AdvertisementDao {
    int insertAds(Advertisement advertisement);//插入广告页面

    List<Advertisement> selectAllAds();//查询所有广告
    List<Advertisement> selectByAdsStatus(int status);//根据广告状态状态查询广告
    List<Advertisement> selectByAdsPriority(int priority);//根据优先级查询广告
    List<Advertisement> selectByCompanyId(int companyId);//根据公司Id查询广告
    List<Advertisement> selectByOrganizerId(int organizerId);//根据展会Id查询广告

    int updateAds(int id,Date startTime,Date endTime,String picture,int priority);//修改广告信息，可修改字段包括广告开始结束时间、广告、优先级
    int updateAdsStatus(int id ,int status);//修改广告状态

    int deleteAllAds();//删除所有数据，测试用
//    int updateAdsEndTime(int id ,Date endTime);//修改广告结束时间
//    int updateAdsStatus(int id ,int status);//修改广告状态
//    int uddateAdsPriority(int id,int priority);//修改广告优先级


}
