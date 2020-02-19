package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;

import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 14:09
 * @Description:
 */
@RestController
@RequestMapping("/exhibition")
public class ExhibitionController {

    @Autowired
    private IExhibitionService exhibitionService;

    @Value("${pagehelper.pageSize}")
    private int pageSize;//一页显示几个，默认为10个

    //------------查询----------------------
    //查询所有
    @GetMapping("/list/{pageNum}")  //test ok
    public PageInfo<Exhibition> queryAllExhibitions(@PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryAllExhibitions();
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据状态分页查询
    @GetMapping("/status/{status}/{pageNum}") //test ok
    public PageInfo<Exhibition> queryExhibitionsByStatus(@PathVariable int status, @PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据关键字分页查询 因为带中文 不好使用路径传参
    @GetMapping("/keyWord") //test ok
    public PageInfo<Exhibition> queryExhibitionsByKeyWord(String keyWord, int pageNum) {
        System.out.println(keyWord);
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByKeyWord(keyWord);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据id查询
    @GetMapping("/id/{id}") //test ok
    public Exhibition queryExhibitionsByKeyWord(@PathVariable int id) {
        return exhibitionService.queryExhibitionByID(id);
    }
    //---------------增删改-------------------------
    @PostMapping("/add")  //test ok
    public Map<String, String> addExhibition(Exhibition exhibition) {
        System.out.println("--------------" + exhibition.toString());
        String value = "";
        String code = "";
        if (exhibitionService.saveExhibition(exhibition) > 0) {
            value = "添加成功";
            code = "005";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //删除
    @PostMapping("/delete/{id}/{userId}")  //test ok
    public Map<String, String> deleteExhibition(@PathVariable int id, @PathVariable int userId) {
        System.out.println("--------------id=" + id + ",userId=" + userId);
        String value;
        String code;
        int i = exhibitionService.deleteExhibition(id, userId);
        if (i == -1) {
            value = "无权限";
            code = "004";
        } else if (i == 1) {
            value = "删除成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //全部改
    @PostMapping("/updateAll")  //test ok
    public Map<String, String> updateExhibition(Exhibition exhibition, int userId) {
        System.out.println("--------------id=" + userId + ",exhibition" + exhibition.toString());
        String value;
        String code;
        int i = exhibitionService.modifyExhibition(exhibition, userId);
        if (i == -1) {
            value = "存在空参数";
            code = "024";
        } else if (i == 1) {
            value = "修改成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //只改状态
    @PostMapping("/updateStatus")  //test ok
    public Map<String, String> updateExhibitionStatus(int id, int userId, int status) {
        System.out.println("--------------id=" + id + ",userId=" + userId + ",status=" + status);
        String value;
        String code;
        int i = exhibitionService.modifyExhibitionStatus(id, userId, status);
        if (i == -1) {
            value = "无权限";
            code = "004";
        } else if (i == 1) {
            value = "修改成功";
            code = "005";
        } else {
            value = "系统异常";
            code = "-001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    //-----------------对用户开放-------------------------
    //用户查询所有展会，一定是通过审核（status=2）的  等待测试
    @GetMapping("/user/queryAll/{pageNum}")
    public PageInfo<Exhibition> queryAllExhibitionsForUser(@PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(2);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }
    //用户查询我的公司的的展会
//    @GetMapping("/user/queryMy/{id}/{pageNum}")
//    public PageInfo<Exhibition> queryAllExhibitionsMyself(@PathVariable int id,@PathVariable int pageNum) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Exhibition> exhibitionList = XXX;
//        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
//        return pageInfo;
//    }















    //------------------测试@NullDisable接口----------------------
    @GetMapping("/testNullArg")
    public List<Exhibition> test() {
        Integer status = null;
        List<Exhibition> exhibitionList = exhibitionService.queryExhibitionsByStatus(status);
        return exhibitionList;
    }

    @GetMapping("/testNullArg2")
    public int test2() {
        String key = "";
        exhibitionService.queryExhibitionsByKeyWord(key);
        return 1;
    }

    @GetMapping("/testNullArg3")
    public int test3() {
        Integer id = 1;
        String e = null;
        UserEmailServiceImpl u = new UserEmailServiceImpl();
        u.bindUserEmail(id, e);
        return 1;
    }
}
