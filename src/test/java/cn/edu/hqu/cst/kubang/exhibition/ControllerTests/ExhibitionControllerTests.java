package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;


import cn.edu.hqu.cst.kubang.exhibition.controller.AdminController;
import cn.edu.hqu.cst.kubang.exhibition.controller.ExhibitionController;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ExhibitionControllerTests {


// * 1、/queryExhibitionSubareaById/{id} 根据展会id查询展会分区信息
// * 2、/queryGoodsByExhibitionId/{id} 据展会id查询展会的所有商品
// * 3、/querySubareaGoodsByExhibitionId/{id} 根据展会id和分区id查询展会分区商品信息
// * 4、/queryReadyToStartExhibitionInfo 返回即将上线的展会信息
// * 5、/queryOngoingExhibitionInfo 返回正在展开的展会


    @Autowired
    private ExhibitionController exhibitionController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Exhibition exhibition;


    @Test
    public void testQueryExhibitionSubareaByExhibitionId() throws Exception{
        //根据展会id查询展会分区信息
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/exhibition/queryExhibitionSubareaById")
                .param("exhibitionId","1298"))
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
    public void testGoodsQueryByExhibitionId() throws Exception{
        //根据展会id查询展会所有商品
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/exhibition/queryGoodsByExhibitionId")
                .param("exhibitionId","1298")
                .param("pageNum","1")
                .param("pageSize","2"))
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
    public void testQuerySubareaGoodsByExhibitionId() throws Exception{
        //根据展会id和二级id查询展会所有商品
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/exhibition/querySubareaGoodsByExhibitionId")
                .param("exhibitionId","1298")
                .param("subareaId","9")
                .param("pageNum","1")
                .param("pageSize","2"))
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
    public void testQueryReadyToStartExhibitionInfo() throws Exception{
        //查询即将开展的展会列表 一页8个
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/exhibition/queryReadyToStartExhibitionInfo/1"))
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
    public void testQueryOngoingExhibitionInfo() throws Exception{
        //根据展会id查询展会分区信息
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/exhibition/queryOngoingExhibitionInfo/1"))
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
}
