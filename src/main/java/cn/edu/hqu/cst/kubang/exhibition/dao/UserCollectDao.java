package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserCollectDao {
    // 查看用户收藏
    List<GoodsNewPojo> queryCollectGoods(Integer userId);

    List<Company> queryCollectCompany(Integer userId);
}
