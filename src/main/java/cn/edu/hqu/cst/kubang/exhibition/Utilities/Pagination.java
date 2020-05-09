package cn.edu.hqu.cst.kubang.exhibition.Utilities;

import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination {

    public static Map<String ,Object> pagination(int pageNum,int pageSize,List<Exhibition> T){
        Map<String,Object> map = new HashMap<>();
        int maxPage = 0;
        String info = null;
        List<Exhibition> middle = null;
        int i = (int)Math.floor(T.size()/pageSize);
        float j = (float)T.size()/pageSize-i;
        if(j!=0)
            maxPage = i+1;
        else
            maxPage = i;
        if(pageNum>0 && pageNum<=maxPage){
            if(pageNum ==maxPage && j!=0){
                info = "残余尾页";
                middle = T.subList((pageNum-1)*pageSize,T.size());
            }else{
                info = "整数页";
                middle = T.subList((pageNum-1)*pageSize,pageSize*pageNum);
            }

        }else{
            info = "页数错误";
        }
        map.put("maxPage",maxPage);
        map.put("info",info);
        map.put("exhibitionList",middle);
        return map;
    }

    public static Map<String ,Object> paginationGoods(int pageNum,int pageSize,List<Goods> T){
        Map<String,Object> map = new HashMap<>();
        int maxPage = 0;
        String info = null;
        List<Goods> middle = null;
        int i = (int)Math.floor(T.size()/pageSize);
        float j = (float)T.size()/pageSize-i;
        if(j!=0)
            maxPage = i+1;
        else
            maxPage = i;
        if(pageNum>0 && pageNum<=maxPage){
            if(pageNum ==maxPage && j!=0){
                info = "残余尾页";
                middle = T.subList((pageNum-1)*pageSize,T.size());
            }else{
                info = "整数页";
                middle = T.subList((pageNum-1)*pageSize,pageSize*pageNum);
            }

        }else{
            info = "页数错误";
        }
        map.put("maxPage",maxPage);
        map.put("info",info);
        map.put("goodsList",middle);
        return map;
    }


    public static Map<String ,Object> paginationAds(int pageNum,int pageSize,List<Advertisement> T){
        Map<String,Object> map = new HashMap<>();
        int maxPage = 0;
        String info = null;
        List<Advertisement> middle = null;
        int i = (int)Math.floor(T.size()/pageSize);
        float j = (float)T.size()/pageSize-i;
        if(j!=0)
            maxPage = i+1;
        else
            maxPage = i;
        if(pageNum>0 && pageNum<=maxPage){
            if(pageNum ==maxPage && j!=0){
                info = "残余尾页";
                middle = T.subList((pageNum-1)*pageSize,T.size());
            }else{
                info = "整数页";
                middle = T.subList((pageNum-1)*pageSize,pageSize*pageNum);
            }

        }else{
            info = "页数错误";
        }
        map.put("maxPage",maxPage);
        map.put("info",info);
        map.put("goodsList",middle);
        return map;
    }

    public static Map<String ,Object> paginationCompany(int pageNum,int pageSize,List<Company> T){
        Map<String,Object> map = new HashMap<>();
        int maxPage = 0;
        String info = null;
        List<Company> middle = null;
        int i = (int)Math.floor(T.size()/pageSize);
        float j = (float)T.size()/pageSize-i;
        if(j!=0)
            maxPage = i+1;
        else
            maxPage = i;
        if(pageNum>0 && pageNum<=maxPage){
            if(pageNum ==maxPage && j!=0){
                info = "残余尾页";
                middle = T.subList((pageNum-1)*pageSize,T.size());
            }else{
                info = "整数页";
                middle = T.subList((pageNum-1)*pageSize,pageSize*pageNum);
            }

        }else{
            info = "页数错误";
        }
        map.put("maxPage",maxPage);
        map.put("info",info);
        map.put("goodsList",middle);
        return map;
    }
}
