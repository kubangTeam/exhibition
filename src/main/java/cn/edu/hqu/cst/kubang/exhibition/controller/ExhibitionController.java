package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.18 14:09
 *  @Description:
 */
@RestController
public class ExhibitionController {

    @Autowired
    private IExhibitionService exhibitionService;

    @Value("${pagehelper.pageSize}")
    private int pageSize;

    //根据状态查询
    @GetMapping("/list/{status}/{pageNum}")
    public PageInfo<Exhibition> queryExhibitionsByStatus(@PathVariable int status,@PathVariable int pageNum) {
        System.out.println("start...");
        PageHelper.startPage(pageNum,pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        System.out.println(pageInfo);
        return pageInfo;
    }

    //测试@NullDisable接口
    @GetMapping("/test")
    public List<Exhibition> test(){
        Integer status = null;
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        return exhibitionList;
    }

    //测试@NullDisable接口
    @GetMapping("/test1")
    public Exhibition test1(){
        Integer id = null;
        Exhibition exhibition = exhibitionService.queryExhibitionByID(id);
        return exhibition;
    }
}
