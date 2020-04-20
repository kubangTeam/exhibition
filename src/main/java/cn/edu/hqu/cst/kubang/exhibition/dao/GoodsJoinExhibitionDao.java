package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.CompanyJoinExhibition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsJoinExhibitionDao {
    int checkGoodsJoinOrNot(int exhibitionId,int goodsId);//根据商品id和展会id，查询该商品是否参加了该展会
    int checkGoodsSubarea(int exhibitionId,int goodsId,int subareaId);//根据商品id、展会id、二级分类id，查询该商品是否参加了该展会且属于该二级分类

}
