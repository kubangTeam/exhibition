package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionGoodsDTO;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
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
    List<Goods> selectGoodsByName(@Param("goodsName")String goodsName, @Param("goodsStatus")Integer goodsStatus);//根据展品名称查询在展或未展商品（goodsStatus=0/1/2/3/4）
    List<Goods> selectGoodsByCompanyId(@Param("companyId")int companyId, @Param("goodsStatus")Integer goodsStatus);//根据公司id查询某状态的商品（goodsStatus=0/1/2/3/4）
    List<Goods> selectGoodsByCategoryId(@Param("categoryId")int categoryId, @Param("goodsStatus")int goodsStatus);
    List<GoodsPic> selectGoodsPicByGoodsId(int goodsId);//查询一个展品的所有图片
    int selectGoodsCount();//查询商品总数
    int selectStatusById(int goodsId);//查询商品在展状态
    int insertGoods(Goods goods);//添加展品
    int insertGoodsPic(GoodsPic goodsPic);//添加展品图片
    int updateGoods(Goods goods);
    int updateStatus(@Param("goodsId")int goodsId, @Param("goodsStatus")int goodsStatus);//修改展品状态
    int updatePriority(@Param("goodsId")int goodsId, @Param("priority")int priority);//修改展品优先级
    int deleteGoods(int goodId);//根据展品ID删除
    int deleteGoodsPic(int picId);//删除展品图片
    String selectCategoryNameById(int categoryId);//根据分类Id查询分类名
    String selectCompanyNameById(int companyId);//根据公司Id查询公司名

    List<String> selectAllGoodsPicById(int id);
    GoodsNewPojo selectGoodsNewById(int id);
    // 通过分类id查找对应的名字
    String selectCategoryNameByCategoryId(int id);

    List<Goods> selectRandomGoods(@Param("num")int num, @Param("goodsStatus")int goodsStatus);
    List<Goods> selectRandomGoodsByCategoryId(@Param("num")int num, @Param("categoryId")int categoryId,@Param("goodsStatus")int goodsStatus);

    List<Goods> selectGoodsByStatus(@Param("goodsStatus")int goodsStatus);
    List<Integer> selectGoodsIdByCompanyId(@Param("companyId")int companyId);

    List<ExhibitionGoodsDTO> listExhibitionGoods(int id);
}
