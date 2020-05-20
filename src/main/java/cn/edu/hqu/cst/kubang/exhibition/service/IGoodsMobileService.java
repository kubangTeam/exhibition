package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionGoodsDTO;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;

import java.util.List;

public interface IGoodsMobileService {
    // 根据展品的id查找它的详情
    GoodsNewDto queryGoodsById(int goodsId);

    // 根据展会的id查找它的所有展品
    ResponseJson<List<ExhibitionGoodsDTO>> getAllExhibitionGoods(Integer exhibitionId);
}
