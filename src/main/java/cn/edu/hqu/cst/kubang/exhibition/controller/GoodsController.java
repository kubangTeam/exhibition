package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author SunChonggao
 * @Date 2020/2/19 13:56
 * @Version 1.0
 * @Description:处理与展品相关的请求
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //从start到end随机取nums个不重复的整数
    public static List getRandomNumList(int nums,int start,int end){
        List list = new ArrayList();
        Random r = new Random();
        while(list.size() != nums){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num)){
                list.add(num);
            }
        }
        return list;
    }
    //展品推荐  个数：recNum  goodsStatus为0的不推荐
    @RequestMapping(path = "/recommend", method =  RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>>  getRecommendGoods(){
        List<Map<String, Object>> list = new ArrayList<>();
        List recId = new ArrayList();
        int recNum = 4; //推荐个数
        recId= getRandomNumList(recNum, 1, 9);
        //System.out.println(recId);
        for(int i = 0; i < recId.size(); i++) {
            int id = (int) recId.get(i);
            if(goodsService.queryGoodsStatus(id) == 1) {
                Goods goods = goodsService.queryGoodsById(id, 1);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("goodsId", goods.getGoodsId());
                map.put("goodsName", goods.getGoodsName());
                map.put("categoryId", goods.getCategoryId());
                map.put("goodsAreaNumber", goods.getGoodsAreaNumber());
                map.put("companyId", goods.getCompanyId());
                map.put("website", goods.getWebsite());
                map.put("originPlace", goods.getOriginPlace());
                map.put("originalPrice", goods.getOriginalPrice());
                map.put("currentPrice", goods.getCurrentPrice());
                map.put("goodIntroduce", goods.getGoodIntroduce());
                map.put("goodsStatus", goods.getGoodsStatus());
                map.put("identifyStatus", goods.getIdentifyStatus());
                map.put("priority", goods.getPriority());
                list.add(map);
                //System.out.println(map.get("goodsId"));
            }
        }
        return list;
    }
    //根据类别Id查询所有在展的商品；
    // 请求参数：展品ID；
    //默认查询在展商品
    @RequestMapping(value = "/query/category", method =  RequestMethod.GET)
    @ResponseBody
    public List<Goods> queryAllGoodsByCategoryId(@RequestParam(value = "categoryId") int categoryId){
        List<Goods> list = new ArrayList<>();
        list = goodsService.queryAllGoodsByCategoryId(categoryId,1);
        return list;
    }
    //根据公司Id查询所有在展的商品;
    //参数：公司Id
    //默认查询在展商品
    @RequestMapping(value = "/query/company", method =  RequestMethod.GET)
    @ResponseBody
    public List<Goods> queryAllGoodsByCompanyId(@RequestParam(value = "companyId") int companyId){
        List<Goods> list = new ArrayList<>();
        list = goodsService.queryAllGoodsByCompanyId(companyId,1);
        return list;
    }
    //根据关键词查询所有在展的商品
    //参数：关键词
    //默认查询在展商品
    @RequestMapping(value = "/query/keyword", method =  RequestMethod.GET)
    @ResponseBody
    public List<Goods> queryAllGoodsByKeyword(@RequestParam(value = "keyword") String keyword){
        List<Goods> list = new ArrayList<>();
        list = goodsService.queryAllGoodsByKeyword(keyword,1);
        return list;
    }
    //添加展品信息
    //参数：展品类
    //错误状态码：-008
    @RequestMapping(value = "/add", method =  RequestMethod.POST)
    public Map<String, String> addGoods(@RequestParam(value = "goods")Goods goods) {
        String value = "";
        String code = "";
        if (goodsService.addGoods(goods) > 0) {
            value = "添加成功";
            code = "005";
        }
        else{
            value = "添加失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
    /*
    修改展品状态
    参数1：展品ID
    参数2：展品状态
    错误状态码：-008
    */
    @RequestMapping(value = "/modify/goodStatus", method =  RequestMethod.POST)
    public Map<String, String> modifyGoodsStatus(@RequestParam(value = "goodsId")int goodsId,
                                        @RequestParam(value = "goodsStatus")int goodsStatus) {
        String value = "";
        String code = "";
        if (goodsService.modifyGoodsStatus(goodsId, goodsStatus) > 0) {
            value = "修改成功";
            code = "005";
        }
        else{
            value = "修改失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
     /*
    修改展品优先级
    参数1：展品ID
    参数2：展品优先级
    错误状态码：-008
    */
    @RequestMapping(value = "/modify/priority", method =  RequestMethod.POST)
    public Map<String, String> modifyGoodsPriority(@RequestParam(value = "goodsId")int goodsId,
                                                 @RequestParam(value = "priority")int priority) {
        String value = "";
        String code = "";
        if (goodsService.modifyGoodsPriority(goodsId, priority) > 0) {
            value = "修改成功";
            code = "005";
        }
        else{
            value = "修改失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
    /*
    从数据库中删除该展品
    参数：展品ID
    错误状态码：-008
    */
    @RequestMapping(value = "/delete", method =  RequestMethod.POST)
    public Map<String, String> deleteGoods(@RequestParam(value = "goodsId")int goodsId) {
        String value = "";
        String code = "";
        if (goodsService.deleteGoods(goodsId) > 0) {
            value = "删除成功";
            code = "005";
        }
        else{
            value = "删除失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }


}
