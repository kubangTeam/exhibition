package cn.edu.hqu.cst.kubang.exhibition.Utilities;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

public class JsonBuilder {
    private Map<String, Object> map = new HashMap<>();

    public ModelAndView getJsonResult(){
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
    public void add(String key, Object value){
        map.put(key,value);
    }
    //新添加的两个方法
    //addRoot是创建json对象的一个根节点
    //addChild是往根节点里添加子节点
    public void addRoot(String root){
        map.put(root,new HashMap<String, Object>());
    }
    public void addChild(String root, String child, Object key){
        try {
            ((HashMap<String, Object>) map.get(root)).put(child, key);
        }
        catch (NullPointerException e){
            System.out.println("root not existed!");
            e.printStackTrace();
        }
    }

    public void clear(){
        map.clear();
    }
}
