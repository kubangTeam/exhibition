package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.18 14:09
 *  @Description:
 */
@RestController
@RequestMapping("/exhibition")
public class ExhibitionController {

    @Autowired
    private IExhibitionService exhibitionService;

    @Value("${pagehelper.pageSize}")
    private int pageSize;//一页显示几个，默认为10个
    //------------查询-----------------------
    //查询所有
    @GetMapping("/list/{pageNum}")
    public PageInfo<Exhibition> queryAllExhibitions(@PathVariable int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryAllExhibitions();
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }
    //根据状态分页查询
    @GetMapping("/status/{status}/{pageNum}")
    public PageInfo<Exhibition> queryExhibitionsByStatus(@PathVariable int status,@PathVariable int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }
    //根据关键字分页查询
    @GetMapping("/keyWord/{keyWord}/{pageNum}")
    public PageInfo<Exhibition> queryExhibitionsByKeyWord(@PathVariable String keyWord,@PathVariable int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByKeyWord(keyWord);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }
    //根据id查询
    @GetMapping("/id/{id}")
    public Exhibition queryExhibitionsByKeyWord(@PathVariable int id) {
        return exhibitionService.queryExhibitionByID(id);
    }
    //---------------增删改-------------------------




    //测试@NullDisable接口
    @GetMapping("/testNullArg")
    public List<Exhibition> test(){
        Integer status = null;
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        return exhibitionList;
    }

    //测试@NullDisable接口
    @GetMapping("/testNullArg2")
    public int test2(){
       String key = "";
       exhibitionService.queryExhibitionsByKeyWord(key);
        return 1;
    }



    //测试@NullDisable接口
    @GetMapping("/testNullArg3")
    public int test3(){
        Integer id = 1;
        String e = null;
        UserEmailServiceImpl u = new UserEmailServiceImpl();
        u.bindUserEmail(id,e);
        return 1;
    }
}
