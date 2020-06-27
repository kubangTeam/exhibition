package cn.edu.hqu.cst.kubang.exhibition.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @Author SunChonggao
 * @Date 2020/6/26 23:39
 * @Version 1.0
 * @Description:
 */
@Controller
public class PageController {
    @RequestMapping(path = "/home",method = RequestMethod.GET)
    public String getHomePage(){
        return "introduce";
    }
    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(){
        return "index";
    }
}
