package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;

public interface IGoodsMobileService {
    // 根据展品的id查找它的详情
    GoodsNewDto queryGoodsById(int goodsId);
}
