package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsJoinExhibition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsJoinExhibitionDao {

    int insertGoodsJoinExhibition(GoodsJoinExhibition goodsJoinExhibition);
    int deleteGoodsJoinExhibition(int id);
    GoodsJoinExhibition checkGoodsSubarea(int exhibitionId,int goodsId,int subareaId);//根据商品id、展会id、二级分类id，查询该商品是否参加了该展会且属于该二级分类
    GoodsJoinExhibition checkGoods(int exhibitionId,int goodsId);//根据商品id、展会id、二级分类id，查询该商品是否参加了该展会且属于该二级分类
    List<GoodsJoinExhibition> selectBySubareaId();
    List<GoodsJoinExhibition> selectByGoodsId();
    List<GoodsJoinExhibition> selectByExhibitionId(int exhibitionId);
    GoodsJoinExhibition selectById();
    List<GoodsJoinExhibition> selectByExhibitionIdAndSubareaId(int exhibitionId,int subareaId);
}
