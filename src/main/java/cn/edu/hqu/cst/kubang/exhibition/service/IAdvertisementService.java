package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 *  @author: sunquan
 *  @Date: 2020.03.22 10.42
 *  @Description: 广告的service层接口
 */
public interface IAdvertisementService {
    Map<String,Object> recommendAds(int pageNum);//推荐广告页，每页推送8个
    String examineAds(int id,int type);//审核广告
}
