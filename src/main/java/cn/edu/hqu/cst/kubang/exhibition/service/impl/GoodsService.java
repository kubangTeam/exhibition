package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public Goods queryGoodsById(int goodsId){
        return goodsDao.selectGoodsById(goodsId);
    }
        //根据公司ID和状态查询在展和不在展的商品
    public PageInfo<Goods> queryAllGoodsByCompanyId(int companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsDao.selectGoodsByCompanyId(companyId, 1);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
        //根据分类ID和状态查询在展商品
    public PageInfo<Goods> queryAllGoodsByCategoryId(int categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsDao.selectGoodsByCompanyId(categoryId, 1);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
        //根据名字查询在展和不在展的商品
    public List<Goods> queryAllGoodsByName(String name) {
        return goodsDao.selectGoodsByName(name,1);
    }
    //查询商品总数
    public int queryGoodsCount(){
        return goodsDao.selectGoodsCount();
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
    //添加展品图片
    public int addGoodsPic(int goodsId,String pic){
        if(pic != null)
            return goodsDao.insertGoodsPic(goodsId, pic);
        else
            return -1;//商品图片为空
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
