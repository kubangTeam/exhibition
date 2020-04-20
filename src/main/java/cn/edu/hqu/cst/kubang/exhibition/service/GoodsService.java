package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsPic;
import cn.edu.hqu.cst.kubang.exhibition.entity.DisplayZone;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Company company;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private DisplayZone goodsType;

    @Autowired
    private GoodsPic goodsPic;




   //查询所有商品
    public List<Goods> queryGoodsALl(){
        return goodsDao.selectAllGoods();
    }

    //根据展品Id查询该展品的图片
    public List<GoodsPic> queryGoodsPic(int goodsId){
        return goodsDao.selectGoodsPicByGoodsId(goodsId);
    }

    //根据公司ID和状态查询在展和不在展的商品
    public PageInfo<Goods> queryAllGoodsByCompanyId(int companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsDao.selectGoodsByCompanyId(companyId, STATE_IS_ON_SHOW);
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
        return goodsDao.selectGoodsByName(name,STATE_IS_ON_SHOW);
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

    /**
     * 根据商品id查询商家信息
     */
    public Company selectCompanyInformationByGoodsId(int goodsId){
        int companyId = goodsDao.selectGoodsById(goodsId).getCompanyId();
        Company company = companyDao.selectCompanyInformationById(companyId);
        return company;
    }


    /**
     * 根据商品id返回补充的商品详情
     * 1、商品类别转换为中文
     * 2、商品所属公司转为中文
     * 3、添加一个商品照片作为前台显示使用
     *
     * 未完成
     */
    public Map<String,Object> selectGoodsInformation(int goodsId){
        Map<String,Object> map = new HashMap<>();
        Goods good  = goodsDao.selectGoodsById(goodsId);
        //获取商家名称
        String companyName =selectCompanyInformationByGoodsId(goodsId).getName();
        //获取分类名称
        int typeId = good.getCategoryId();
        //String goodType = goodsTypeDao.selectGoodsTypeById(typeId).getGoodsType();
        //获取商品封面

        map.put("goodInformation",good);
        //map.put("goodType",goodType);
        map.put("companyName",companyName);
        return map;



    }


}
