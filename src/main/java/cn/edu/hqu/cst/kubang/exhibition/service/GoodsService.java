package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.Utilities.Pagination;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @Author SunChonggao
 * @Date 2020/2/18 17:48
 * @Version 1.0
 * @Description:
 */
@Service
@Slf4j
@EnableScheduling
public class GoodsService implements Constants {

    private GoodsDao goodsDao;

    private Goods goods;

    private Company company;

    private  CompanyDao companyDao;

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Value("${pagehelper.pageSize3}")
    private int pageSize3;//一页显示4个
    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个

    ListeningExecutorService executorService = MoreExecutors.
            listeningDecorator(Executors.newFixedThreadPool(1));

    @Resource(name = "redisKeyDb")
    private RedisTemplate<String, Object> redisKeyDb;

    @Autowired
    public GoodsService(GoodsDao goodsDao, Goods goods, Company company, CompanyDao companyDao) {
        this.goodsDao = goodsDao;
        this.goods = goods;
        this.company = company;
        this.companyDao = companyDao;
    }
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
        List<Goods> list = this.insertImageIntoGoods(goodsDao.selectGoodsByCompanyId(companyId,null));
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //根据公司ID和状态查询在展和不在展的商品
    public PageInfo<Goods> queryGoodsByCompanyIdAndStatus(int companyId,int status, int pageNum, int pageSize) {
        if(status<0 || status>4) {
            return null;
        }else{
            PageHelper.startPage(pageNum, pageSize);
            List<Goods> list = this.insertImageIntoGoods(goodsDao.selectGoodsByCompanyId(companyId,status));
            PageInfo<Goods> pageInfo = new PageInfo<>(list);
            return pageInfo;
        }

    }




    //根据公司ID和状态查询在展和不在展的商品
    public PageInfo<Goods> queryByGoodsStatus(int status,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list =null;
        if(status<0 || status>4){
            list = null;
        }else{
            list = this.insertImageIntoGoods(goodsDao.selectGoodsByStatus(status));
        }
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }


    //根据分类ID和状态查询在展商品
    public Map<String,Object> queryAllGoodsByCategoryId(int categoryId,int pageNum) {
        Map<String,Object> map = new HashMap<>();
        List<Goods> list=null;
        if(goodsDao.selectGoodsByCategoryId(categoryId, STATE_IS_ON_SHOW)!=null){
            list = goodsDao.selectGoodsByCategoryId(categoryId, STATE_IS_ON_SHOW);
            list = this.insertImageIntoGoods(list);
        }
        map = Pagination.paginationGoods(pageNum,pageSize2,list);
        return map;
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
        //用户提交优先级审核，修改商品原来的状态，变为待审核
        Integer i =null;
        Integer j =null;
        i = goodsDao.updateStatus(goodsId,STATE_IS_ON_READY);
        j=  goodsDao.updatePriority(goodsId, priority);
        if(i==1 && j==1)
            return 1;
        else
            return 0;
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

    private List<Goods> insertImageIntoGoods(List<Goods> list){
        for(Goods goods : list) {
            if(goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId())!=null){
                List<GoodsPic> goodsPics= goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId());
                GoodsPic goodsPic = goodsPics.get(0);
                String image = goodsPic.getPic();
                goods.setImage(image);
            }else {
                list.remove(goods);
            }
        }
        return list;
    }

    public Company selectCompanyInformationByGoodsId(int goodsId) {
        goods = goodsDao.selectGoodsById(goodsId);
        company =companyDao.selectCompanyInformationById(goods.getCompanyId());
        return  company;
    }
   public List<Goods> getRandomGoods(int num, int categoryId){
       List<Goods> list;
       if(categoryId == 0)
           list =  this.insertImageIntoGoods(goodsDao.selectRandomGoods(num, STATE_IS_ON_SHOW));
       else
           list =  this.insertImageIntoGoods(goodsDao.selectRandomGoodsByCategoryId(num,categoryId, STATE_IS_ON_SHOW));
       if(list.size() == num)
           return list;
       else {
           log.info("推荐展品异常，个数为"+list.size()+",应该为"+num);
           return getRandomGoods(num, categoryId);
       }
   }

}
