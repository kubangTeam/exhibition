package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsPic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/2/18 17:48
 * @Version 1.0
 * @Description:
 */
@Service
public class GoodsService implements Constants {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private Goods goods;

    @Autowired
    private Company company;

    @Autowired
    private  CompanyDao companyDao;

    //查询展品
        //根据ID和状态查询在展和不在展的商品
    public Goods queryGoodsById(int goodsId){
        Goods goods = goodsDao.selectGoodsById(goodsId);
        GoodsPic goodsPic = goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId()).get(0);
        String image = goodsPic.getPic();
        goods.setImage(image);
        return goods;
    }
        //查询所有商品
    public List<Goods> queryGoodsALl(){
        return this.insertImageIntoGoods(goodsDao.selectAllGoods());
    }
    //根据展品Id查询该展品的图片
    public List<GoodsPic> queryGoodsPic(int goodsId){
        return goodsDao.selectGoodsPicByGoodsId(goodsId);
    }

    //根据公司ID和状态查询在展和不在展的商品
    public PageInfo<Goods> queryAllGoodsByCompanyId(int companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = this.insertImageIntoGoods(goodsDao.selectGoodsByCompanyId(companyId, STATE_IS_ON_SHOW));
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
        //根据分类ID和状态查询在展商品
    public PageInfo<Goods> queryAllGoodsByCategoryId(int categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsDao.selectGoodsByCompanyId(categoryId, STATE_IS_ON_SHOW);

        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
        //根据名字查询在展和不在展的商品
    public List<Goods> queryAllGoodsByName(String name) {
        return this.insertImageIntoGoods(goodsDao.selectGoodsByName(name,STATE_IS_ON_SHOW));
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
    public int addGoodsPic(GoodsPic goodsPic){
        if(goodsPic != null)
            return goodsDao.insertGoodsPic(goodsPic);
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
    //删除展品图片
    public int deleteGoodsPic(int picId){
        return goodsDao.deleteGoodsPic(picId);
    }
    private List<Goods> insertImageIntoGoods (List<Goods> list){
        for(Goods goods : list) {
            GoodsPic goodsPic = goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId()).get(0);
            String image = goodsPic.getPic();
            goods.setImage(image);
        }
        return list;
    }


    public Company selectCompanyInformationByGoodsId(int goodsId) {
        goods = goodsDao.selectGoodsById(goodsId);
        company =companyDao.selectCompanyInformationById(goods.getCompanyId());
        return  company;
    }
}
