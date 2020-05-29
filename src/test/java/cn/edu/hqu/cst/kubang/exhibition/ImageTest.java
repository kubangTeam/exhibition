package cn.edu.hqu.cst.kubang.exhibition;

import cn.edu.hqu.cst.kubang.exhibition.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/5/27 18:21
 * @Version 1.0
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageTest {
    @Test
    public void testGenerateThumbnail2Directory() throws IOException {
        String path = "D:\\Programming\\CouponProject\\轮播图";
        ImageUtil.generateDirectoryThumbnail(path);
    }
}
