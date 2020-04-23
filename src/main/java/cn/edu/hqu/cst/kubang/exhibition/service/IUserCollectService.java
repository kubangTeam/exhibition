package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;

import java.util.List;
import java.util.Map;

public interface IUserCollectService {
    Integer addCollectGoods(int userId, int goodsId);

    Integer addCollectCompany(int userId, int companyId);

    List<GoodsNewDto> queryCollectGoods(int userId);

    List<Map<String,Object>> queryCollectCompany(int userId);
}
