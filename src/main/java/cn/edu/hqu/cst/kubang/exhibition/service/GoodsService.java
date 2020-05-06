package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.Utilities.Pagination;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
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

    @Value("${pagehelper.pageSize3}")
    private int pageSize3;//一页显示4个

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
        List<Goods> list = this.insertImageIntoGoods(goodsDao.selectGoodsByCompanyId(companyId, STATE_IS_ON_SHOW));
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
        map = Pagination.paginationGoods(pageNum,pageSize3,list);
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
   /* public List getGoodsIdInRedis(){
        Set sets = redisKeyDb.opsForZSet().range("Goods",0, -1);
        return new ArrayList<>(sets);
    }
    public void addGoodsIntoRedis(int goodsId){
        redisKeyDb.opsForZSet().add("Goods", goodsId, 0);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public boolean updateGoodsInRedis()throws Exception{
        ListenableFuture<Boolean> future = executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<Goods> list = goodsDao.selectAllGoods();
                System.out.println(list.size());
                for(Goods goods : list){
                    redisKeyDb.opsForZSet().add("Goods", goods.getGoodsId(), 0);
                }
                return true;
            }
        });

        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean v) {
                log.info("@Goods: update redis data for Goods successfully");
            }
            @Override
            public void onFailure(Throwable throwable) {
                log.info("@HotWord: fail to update redis data for goods, message is {}", throwable.getMessage());
            }
            });
        return true;


    }*/
   public List<Goods> getRandomGoods(int num, int categoryId){
       List<Goods> list = this.insertImageIntoGoods(goodsDao.selectRandomGoods(num, categoryId));
       return list;
   }
}
