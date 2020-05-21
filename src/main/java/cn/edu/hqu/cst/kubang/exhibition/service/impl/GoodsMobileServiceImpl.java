package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionGoodsDTO;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.service.IGoodsMobileService;
import cn.edu.hqu.cst.kubang.exhibition.util.ConvertBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.04.23 14:43
 *  @Description:
 */
@Service
public class GoodsMobileServiceImpl implements IGoodsMobileService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private CompanyDao companyDao;

    @Override
    public GoodsNewDto queryGoodsById(int goodsId) {
        GoodsNewPojo goods = goodsDao.selectGoodsNewById(goodsId);
        String category = goodsDao.selectCategoryNameByCategoryId(goods.getCategoryId());
        String companyName = companyDao.queryCompanyNameById(goods.getCompanyId());
        List<String> picture = goodsDao.selectAllGoodsPicById(goods.getGoodsId());
        GoodsNewDto goodsNewDto = ConvertBean.pojoToDto(goods, category, companyName, picture);
        return goodsNewDto;
    }

    @Override
    @NullDisable
    public ResponseJson<List<ExhibitionGoodsDTO>> getAllExhibitionGoods(Integer exhibitionId) {
            List<ExhibitionGoodsDTO> list = goodsDao.listExhibitionGoods(exhibitionId);
            return new ResponseJson(true,"005","查询成功",list);
    }


}
