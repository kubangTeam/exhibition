package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//展会广告页面
@Controller
@RequestMapping("/ads")
public class AdsController {
    @RequestMapping("getAds")
    public ModelAndView getAds(){
        JsonBuilder json = new JsonBuilder();
        return json.getJsonResult();
    }
}
