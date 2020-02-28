package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author SunChonggao
 * @Date 2020/2/18 17:48
 * @Version 1.0
 * @Description:
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public Goods queryGoodsById(int goodsId, int goodsStatus){
        return goodsDao.selectGoodsById(goodsId, goodsStatus);
    }
    public int queryGoodsStatus(int goodsId){
        return goodsDao.selectStatusById(goodsId);
    }
}
