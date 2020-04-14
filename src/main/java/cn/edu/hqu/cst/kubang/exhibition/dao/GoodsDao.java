package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsPic;
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
    List<Goods> selectAllGoods();//查询所有在展商品
    Goods selectGoodsById(int goodsId);//根据展品ID查询商品
    List<Goods> selectGoodsByName(@Param("goodsName")String goodsName, @Param("goodsStatus")int goodsStatus);//根据展品名称查询在展或未展商品（goodsStatus=1/0）
    List<Goods> selectGoodsByCompanyId(@Param("companyId")int companyId, @Param("goodsStatus")int goodsStatus);//根据公司id查询该公司的在展或未展商品（goodsStatus=1/0）
    List<GoodsPic> selectGoodsPicByGoodsId(int goodsId);//查询一个展品的所有图片
    int selectGoodsCount();//查询商品总数
    int selectStatusById(int goodsId);//查询商品在展状态
    int insertGoods(Goods goods);//添加展品
    int insertGoodsPic(GoodsPic goodsPic);//添加展品图片
    int updateStatus(@Param("goodsId")int goodsId, @Param("goodsStatus")int goodsStatus);//修改展品状态
    int updatePriority(@Param("goodsId")int goodsId, @Param("priority")int priority);//修改展品优先级
    int deleteGoods(int goodId);//根据展品ID删除
    int deleteGoodsPic(int picId);//删除展品图片
}
