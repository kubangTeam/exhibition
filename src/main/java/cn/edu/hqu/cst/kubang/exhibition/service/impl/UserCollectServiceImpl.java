package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.CompanyDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCollectDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewDto;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsNewPojo;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserCollectService;
import cn.edu.hqu.cst.kubang.exhibition.util.ConvertBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @author: 邢佳成
 *  @Date: 2020.04.23 12:24
 *  @Description:
 */
@Service
public class UserCollectServiceImpl implements IUserCollectService {

    @Resource
    private UserCollectDao userCollectDao;

    @Resource
    private CompanyDao companyDao;

    @Resource
    private GoodsDao goodsDao;

    @Override
    public Integer addCollectGoods(int userId, int goodsId) {
        return null;
    }

    @Override
    public Integer addCollectCompany(int userId, int companyId) {
        return null;
    }

    @Override
    public List<GoodsNewDto> queryCollectGoods(int userId) {
        List<GoodsNewPojo> goodsNewPojos = userCollectDao.queryCollectGoods(userId);
        List<GoodsNewDto> goodsNewDtoList = new ArrayList<>();
        goodsNewPojos.forEach(item->{
            // 根据categoryId查找对应的名字
            String category = goodsDao.selectCategoryNameByCategoryId(item.getCategoryId());;
            // 根据companyId查找对应的名字
            String companyName = companyDao.queryCompanyNameById(item.getCompanyId());
            // 根据展品id查找它的轮播图
            List<String> picture = goodsDao.selectAllGoodsPicById(item.getGoodsId());
            goodsNewDtoList.add(ConvertBean.pojoToDto(item,category,companyName,picture));
        });
//        goodsNewDtoList.forEach(System.out::println);
        return goodsNewDtoList;
    }

    @Override
    public List<Map<String,Object>> queryCollectCompany(int userId) {
        List<Company> companies = userCollectDao.queryCollectCompany(userId);
        List<Map<String,Object>> res = new ArrayList<>();
        companies.forEach(item->{
            Map<String,Object> map = new HashMap<>();
            map.put("id",item.getId());
            map.put("name",item.getName());
            map.put("type",item.getType());
            map.put("headPicture",item.getHeadPicture());
            res.add(map);
        });
        return res;
    }
}
