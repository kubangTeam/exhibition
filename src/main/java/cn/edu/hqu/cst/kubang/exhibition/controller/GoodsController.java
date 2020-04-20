package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import cn.edu.hqu.cst.kubang.exhibition.entity.GoodsPic;
import cn.edu.hqu.cst.kubang.exhibition.service.GoodsService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author SunChonggao
 * @Date 2020/2/19 13:56
 * @Version 1.0
 * @Description:处理与展品相关的请求
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "商品管理服务")
public class GoodsController implements Constants {
    @Autowired
    private GoodsService goodsService;
    @Value("${exhibition.path.domain}")
    private String domain;
    @Value("${exhibition.path.upload}")
    private String uploadPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    //从start到end随机取nums个不重复的整数
    public List getRandomNumList(int nums, int start, int end) {
        List list = new ArrayList();
        Random r = new Random();
        while (list.size() != nums) {
            int num = r.nextInt(end - start) + start;
            //id不重复且该展品的状态为在展
            if (!list.contains(num) && goodsService.queryGoodsStatus(num) == 1) {
                list.add(num);
            }
        }
        return list;
    }

    //展品推荐  个数：recNum  goodsStatus为0的不推荐
    @ApiOperation(value = "展品推荐", notes = "无参，重新请求可实现“换一批”")
    @RequestMapping(path = "/recommend", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getRecommendGoods() {
        List<Map<String, Object>> list = new ArrayList<>();
        int recNum = COUNT_RECOMMEND;
        List recId = getRandomNumList(recNum, 0, goodsService.queryGoodsCount());
        for (Object object:recId) {
            int id = Integer.parseInt(object.toString());
            if (goodsService.queryGoodsStatus(id) == STATE_IS_ON_SHOW) {
                Goods goods = goodsService.queryGoodsById(id);
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
                map.put("goodIntroduce", goods.getGoodsIntroduce());
                map.put("goodsStatus", goods.getGoodsStatus());
                // map.put("identifyStatus", goods.getIdentifyStatus());
                map.put("priority", goods.getPriority());
                list.add(map);
                //System.out.println(map.get("goodsId"));
            }
        }
        return list;
    }
    //热门展品  个数：recNum  goodsStatus为0的不推荐
    @ApiOperation(value = "热门展品", notes = "无参，重新请求可实现“换一批”")
    @RequestMapping(path = "/hot", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getHotGoods() {
        List<Map<String, Object>> list =  this.getRecommendGoods();
        return list;
    }
    //根据展品Id查询所有在展的商品；
    //请求参数：展品ID；
    @ApiOperation(value = "根据展品Id查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "展品ID", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/query/goodsId", method = RequestMethod.GET)
    @ResponseBody
    public Goods queryGoodsById(@RequestParam(value = "goodsId") int goodsId) {
        Goods goods  = goodsService.queryGoodsById(goodsId);
        return goods;
    }
    //根据类别Id查询所有在展的商品；
    // 请求参数：类别ID；
    //默认查询在展商品
    @ApiOperation(value = "根据类别Id查询所有在展的商品", notes = "分页查询，，默认查询在展，结果按优先级升序排列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "类别ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/query/category", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Goods> queryAllGoodsByCategoryId(@RequestParam(value = "categoryId") int categoryId,
                                                     @RequestParam(value = "pageNum") int pageNum,
                                                     @RequestParam(value = "pageSize") int pageSize) {
        PageInfo<Goods> pageInfo = goodsService.queryAllGoodsByCategoryId(categoryId,pageNum,pageSize);
        return pageInfo;
    }
    //根据公司Id查询所有在展的商品;
    //参数：公司Id8
    //默认查询在展商品
    @ApiOperation(value = "根据公司Id查询所有在展的商品", notes = "分页查询，默认查询在展商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "公司Id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/query/company", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Goods> queryAllGoodsByCompanyId(@RequestParam(value = "companyId") int companyId,
                                                    @RequestParam(value = "pageNum") int pageNum,
                                                    @RequestParam(value = "pageSize") int pageSize) {
        PageInfo<Goods> pageInfo = goodsService.queryAllGoodsByCompanyId(companyId,pageNum,pageSize);
        return pageInfo;
    }

    //根据关键词查询所有在展的商品
    //参数：关键词
    //默认查询在展商品
    @RequestMapping(value = "/query/keyword", method = RequestMethod.GET)
    @ApiOperation(value = "根据关键词查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public List<Goods> queryAllGoodsByKeyword(@RequestParam(value = "keyword") String keyword) {
        List<Goods> list = new ArrayList<>();
        list = goodsService.queryAllGoodsByName(keyword);
        return list;
    }

    //添加展品信息
    //参数：展品类
    //错误状态码：-008
    @ApiOperation(value = "添加展品信息，支持图片批量上传", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods", value = "展品对象", required = true, dataType = "Goods", paramType = "body"),
            @ApiImplicitParam(name = "files", value = "文件数组", required = true, dataType = "MultipartFile[]", paramType = "query")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, String> addGoods(@RequestBody Goods goods,
                                        @RequestParam(value = "files") MultipartFile[] files,
                                        HttpServletRequest request) throws IOException {
        String infoValue;
        String picValue = "";
        String code;
        //String path = request.getServletContext().getRealPath("/GoodsPictures");
        if (goodsService.addGoods(goods) > 0 ) {
            for(MultipartFile file:files){
                if(file.isEmpty())
                    picValue = "未选择文件";
                else {
                    GoodsPic goodsPic = new GoodsPic();
                    goodsPic.setPic(this.uploadFile(file));
                    goodsPic.setGoodsId(goods.getGoodsId());
                    goodsService.addGoodsPic(goodsPic);
                }
            }
            infoValue = "添加成功";
            code = "005";
        } else {
            infoValue = "添加失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", infoValue + picValue);
        map.put("code", code);
        return map;
    }
    //上传展品图片
    @ApiOperation(value = "单张上传展品图片（已知展品Id）", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "展品图片", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "goodId", value = "展品Id", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/upload/picture", method = RequestMethod.POST)
    public Map<String,String> uploadPicture(@RequestParam(value = "file") MultipartFile file,
                                            @RequestParam(value = "goodsId")int goodsId,
                                            HttpServletRequest httpServletRequest) throws IOException {
        String value;
        String code;
        //String path = httpServletRequest.getServletContext().getRealPath("/GoodsPictures");
        if(file.isEmpty()){
            value = "未选择文件";
            code = "021";
        }
        else{
            GoodsPic goodsPic = new GoodsPic();
            goodsPic.setPic(this.uploadFile(file));
            goodsPic.setGoodsId(goodsId);
            goodsService.addGoodsPic(goodsPic);
            value = "上传成功";
            code = "005";

        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
    @ApiOperation(value = "通过url获取展品图片")
    @RequestMapping(value = "/goodsPic/{fileName}" , method = RequestMethod.GET)
    public void getPic(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //服务器存放的路径
        fileName = uploadPath + "/" +fileName;
        //获取fileName的后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/"+suffix);
        try (
                ServletOutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
        ){
            byte[] buffer = new byte[1024];//建立缓冲区，每次直接写出1024个字节
            int b ;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            System.out.println("读取图片失败："+e.getMessage());
        }
    }
    /*
    修改展品状态
    参数1：展品ID
    参数2：展品状态
    错误状态码：-008
    */
    @ApiOperation(value = "修改展品状态", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "展品ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsStatus", value = "展品状态", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/modify/goodStatus", method = RequestMethod.POST)
    public Map<String, String> modifyGoodsStatus(@RequestParam(value = "goodsId") int goodsId,
                                                 @RequestParam(value = "goodsStatus") int goodsStatus) {
        String value = "";
        String code = "";
        if (goodsService.modifyGoodsStatus(goodsId, goodsStatus) > 0) {
            value = "修改成功";
            code = "005";
        } else {
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
    @ApiOperation(value = "修改展品优先级", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "展品ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "priority", value = "展品优先级", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/modify/priority", method = RequestMethod.POST)
    public Map<String, String> modifyGoodsPriority(@RequestParam(value = "goodsId") int goodsId,
                                                   @RequestParam(value = "priority") int priority) {
        String value = "";
        String code = "";
        if (goodsService.modifyGoodsPriority(goodsId, priority) > 0) {
            value = "修改成功";
            code = "005";
        } else {
            value = "修改失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }

    /*
    删除该展品（将展品状态改为2，不从数据库删除）
    参数：展品ID
    错误状态码：-008
    */
    @ApiOperation(value = "删除展品（将展品状态改为2，不从数据库删除）", notes = "错误状态码：-008")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "展品ID", required = true, dataType = "int", paramType = "query")
    })
    @DeleteMapping(value = "/delete")
    public Map<String, String> deleteGoods(@RequestParam(value = "goodsId") int goodsId) {
        String value = "";
        String code = "";
        if (goodsService.modifyGoodsStatus(goodsId,STATE_IS_DELETED) > 0) {
            value = "删除成功";
            code = "005";
        } else {
            value = "删除失败";
            code = "-008";
        }
        Map<String, String> map = new HashMap<>();
        map.put("response", value);
        map.put("code", code);
        return map;
    }
    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();  // 获取上传图像的原始文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 获取后缀名
        //为了避免用户传递的图像文件名称相同，需要重新给上传的图像文件命名
        fileName = UUID.randomUUID() + suffixName;
        // 创建文件实例
        File dest = new File(uploadPath + "/" + fileName);
        // 如果文件目录不存在，创建目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            System.out.println("创建目录" + dest);
        }
        // 存储文件
        file.transferTo(dest);
        //展品图片Web访问路径
        String url = domain + contextPath + "/goods/goodsPic/" + fileName;
        return url;
    }

}
