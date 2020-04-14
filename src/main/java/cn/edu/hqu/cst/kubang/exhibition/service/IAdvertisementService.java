package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import com.github.pagehelper.PageInfo;

/**
 *  @author: sunquan
 *  @Date: 2020.03.22 10.42
 *  @Description: 广告的service层接口
 */
public interface AdvertisementService {

    PageInfo<Exhibition> recommendAds();//推荐广告页

}
