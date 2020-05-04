package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.GoodsController;
import cn.edu.hqu.cst.kubang.exhibition.dao.GoodsDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.LogUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
@AutoConfigureMockMvc
public class goodsControllerTest {
    @Autowired
    private GoodsController goodsController;

    @Autowired
    private MockMvc mockMvc;



//    1、/recommend 展品推荐
// * 2、/query/goodsId 根据ID查询展品
// * 3、/query/category 根据类别查询展品
// * 4、/query/company 根据公司Id查询在展展品
// * 5、/query/keyword 关键字查询所有在展的商品
// * 6、/add 添加展品信息
// * 7、/upload/picture 上传单张展品图片
// * 8、/goodsPic/{fileName} 通过url获取展品图片
// * 9、/modify/priority 修改展品优先级
// * 10、/delete 删除展品
    @Autowired
    private Goods goods;
    @Autowired
    private GoodsDao goodsDao;
    @Before
    public void before(){
        goods.setGoodsName("测试商品");
        goods.setCategoryId(1);
        goods.setCompanyId(1);
        goods.setCurrentPrice("10");
        goods.setGoodsStatus(1);
        goods.setPriority(0);
        if(goodsDao.insertGoods(goods)==1)
            System.out.println("保存成功");

    }

    @After
    public void after(){
        if(goodsDao.deleteGoods(goods.getGoodsId())==1)
            System.out.println("删除成功");
    }

    @Test
    public void testGoodsRecommend() throws Exception{
        //查询展品推荐
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/recommend"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testGoodsHot() throws Exception{
        //查询热门展品
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/hot"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testFoursGoods() throws Exception{
        //查询热门展品 根据商品分类
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/fourGoods")
                .param("categoryId","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testQueryByGoodsId() throws Exception{
        //查询热门展品 根据商品分类
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/query/goodsId")
                .param("goodsId","2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testGoodsByCategory() throws Exception{
        //根据商品分类查询商品
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/query/category")
                .param("categoryId","1")
                .param("pageNum","1")
                .param("pageSize","10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testQueryByKeyword() throws Exception{
        //查询热门展品 根据商品分类
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/goods/query/keyword")
                .param("keyword","土豆"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }

    @Test
    public void testAddGoods() throws Exception{
        //转换为json
        String requestJson = JSONObject.toJSONString(goods);
        //查询热门展品 根据商品分类
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/goods/add")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        //mvcResult.andDo(print()).andExpect(status().isOk());
        int status=mvcResult.getResponse().getStatus();
        String content =mvcResult.getResponse().getContentAsString();
        System.out.println(status);
        System.out.println(content);
        Assert.assertEquals(200,status);
        Assert.assertTrue(content.length()>0);//里面是一个Boolean 判断
    }



//    /**
//     * 测试上传文件（txt文件、jpg文件）
//     */
//    @Test
//    public void testUploadFile() throws Exception {
//        File file = new File("/Users/sunquan/Downloads/test.txt");
//        MockMultipartFile firstFile = new MockMultipartFile("file", "test.txt",
//                "text/plain", new FileInputStream(file));
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/goods/uploadFileTest")
//                .file(firstFile))//文件
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    /**
//     * 测试上传文件（txt文件、jpg文件）
//     */
//    @Test
//    public void testUploadFilePic() throws Exception {
//        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
//        String goodsId = "1";
//        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
//                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/goods/upload/picture")
//                .file(firstFile)//文件
//                .param("goodsId", goodsId))//参数
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }






}
