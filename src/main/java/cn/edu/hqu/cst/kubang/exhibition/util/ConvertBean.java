package cn.edu.hqu.cst.kubang.exhibition.util;

import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserIntegral;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserIntegralDTO;

import java.util.Date;
import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.04.23 11:49
 *  @Description:
 */
public class ConvertBean {
    public static GoodsNewDto pojoToDto(GoodsNewPojo goods, String categoryName, String companyName, List<String> picture){
        int id = goods.getGoodsId();
        String name = goods.getGoodsName();
        String introduction = goods.getGoodsIntroduce();
        String areaNumber = goods.getGoodsAreaNumber();
        int companyId = goods.getCompanyId();
        String website = goods.getWebsite();
        String originPlace = goods.getOriginPlace();
        String originalPrice = goods.getOriginalPrice();
        String currentPrice = goods.getCurrentPrice();
        Date startTime = goods.getStartTime();
        Date endTime = goods.getEndTime();
        return new GoodsNewDto(id,name,introduction,areaNumber
                ,website,companyId,companyName
                ,picture,originalPrice,currentPrice
                ,originPlace,categoryName,startTime,endTime);
    }
    public static UserIntegralDTO pojoToDto(UserIntegral userIntegral){
        UserIntegralDTO userIntegralDTO = new UserIntegralDTO();
        userIntegralDTO.setIntegral(userIntegral.getIntegral())
                .setStatus(userIntegral.getStatus())
                .setTime(userIntegral.getTime());
        return userIntegralDTO;
    }
}
