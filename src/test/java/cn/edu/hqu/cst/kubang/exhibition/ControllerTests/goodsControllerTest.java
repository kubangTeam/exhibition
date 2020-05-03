package cn.edu.hqu.cst.kubang.exhibition.ControllerTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.controller.GoodsController;
import com.mysql.cj.util.LogUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class goodsControllerTest {
    @Autowired
    private GoodsController goodsController;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * 在每一次测试前构建mvc环境
     */
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试上传文件（txt文件、jpg文件）
     */
    @Test
    public void testUploadFile() throws Exception {
        File file = new File("/Users/sunquan/Downloads/test.txt");
        MockMultipartFile firstFile = new MockMultipartFile("file", "test.txt",
                "text/plain", new FileInputStream(file));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/goods/uploadFileTest")
                .file(firstFile))//文件
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试上传文件（txt文件、jpg文件）
     */
    @Test
    public void testUploadFilePic() throws Exception {
        File file = new File("/Users/sunquan/Downloads/psb.jpeg");
        String goodsId = "1";
        MockMultipartFile firstFile = new MockMultipartFile("file", "psb.jpeg",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/goods/upload/picture")
                .file(firstFile)//文件
                .param("goodsId", goodsId))//参数
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGoodsByCategory() throws Exception{
        //根据展会id查询展会分区信息
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

}
