package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserCollectDao {
    // 收藏的商品
    List<GoodsNewPojo> queryCollectGoods(Integer userId);

    // 收藏的公司
    List<Company> queryCollectCompany(Integer userId);

    // 增加收藏展品
    int saveCollectGoods(@Param("userId") Integer userId,@Param("goodsId")Integer goodsId);

    // 增加收藏公司
    int saveCollectCompany(@Param("userId") Integer userId,@Param("companyId")Integer companyId);

    // 根据userId和goodsId 查找有没有对应的记录 0表示没有
    Integer queryNumForCollectGoods(@Param("userId") Integer userId,@Param("goodsId")Integer goodsId);

    // 根据userId和companyId查找有没有对应的记录 0表示没有
    Integer queryNumForCollectCompany(@Param("userId") Integer userId,@Param("companyId")Integer companyId);
}
