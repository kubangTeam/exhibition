package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Author SunChonggao
 * @Date 2020/2/19 13:56
 * @Version 1.0
 * @Description:处理与展品相关的请求
 */
@Controller
@RequestMapping("/Goods")
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
}
