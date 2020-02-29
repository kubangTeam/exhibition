package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/2/16 08:53
 * @Version 1.0
 * @Description:展品Dao层
 */
@Mapper
@Repository
public interface GoodsDao {
    Goods selectGoodsById(@Param("goodsId")int goodsId, @Param("goodsStatus")int goodsStatus);//根据展品ID查询在展或未展商品（goodsStatus=1/0）
    Goods selectGoodsByName(@Param("goodsName")String goodsName, @Param("goodsStatus")int goodsStatus);//根据展品名称查询在展或未展商品（goodsStatus=1/0）
    List<Goods> selectGoodsByCompanyId(@Param("companyId")int companyId, @Param("goodsStatus")int goodsStatus);//公司名称在展或未展商品（goodsStatus=1/0）
    List<Goods> selectGoodsBysByKeyword(@Param("keyword")String keyword,@Param("goodsStatus")int goodsStatus);//根据关键词查询在展或未展商品（goodsStatus=1/0）
    int selectStatusById(int goodsId);//查询商品在展状态
    boolean insertGoods(Goods goods);//添加展品
    boolean updateStatus(@Param("goodsId")int goodsId, @Param("goodsStatus")int goodsStatus);//修改展品状态
    boolean deleteGoods(int goodId);//根据展品ID删除
}
