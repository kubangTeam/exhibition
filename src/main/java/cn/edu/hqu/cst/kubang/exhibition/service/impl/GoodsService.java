package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //查询展品
        //根据ID和状态查询在展和不在展的商品
    public Goods queryGoodsById(int goodsId, int goodsStatus){
        return goodsDao.selectGoodsById(goodsId, goodsStatus);
    }
        //根据公司ID和状态查询在展和不在展的商品
    public List<Goods> queryAllGoodsByCompanyId(int companyId, int goodsStatus) {
        return goodsDao.selectGoodsByCompanyId(companyId, goodsStatus);
    }
        //根据公司ID和状态查询在展和不在展的商品
    public List<Goods> queryAllGoodsByCategoryId(int categoryId, int goodsStatus) {
        return goodsDao.selectGoodsByCompanyId(categoryId, goodsStatus);
    }
        //根据关键词查询在展和不在展的商品
    public List<Goods> queryAllGoodsByKeyword(String keyword, int goodsStatus) {
        return goodsDao.selectGoodsByKeyword(keyword, goodsStatus);
    }
        //查询展品的是否在展
    public int queryGoodsStatus(int goodsId){
        return goodsDao.selectStatusById(goodsId);
    }
    //添加展品
    public int addGoods(Goods goods){
        if(goods != null)
            return goodsDao.insertGoods(goods);
        else
            return -1;//商品为空
    }
    //修改展品优先级
    public int modifyGoodsPriority(int goodsId, int priority){
        return goodsDao.updatePriority(goodsId, priority);
    }
    //修改展品在展状态
    public int modifyGoodsStatus(int goodsId, int goodsStatus) {
        return goodsDao.updateStatus(goodsId, goodsStatus);
    }
    //删除展品
    public int deleteGoods(int goodsId){
        return goodsDao.deleteGoods(goodsId);
    }

}
