package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AccountServiceImp;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.AdvertisementServiceImpl;
import cn.edu.hqu.cst.kubang.exhibition.service.impl.UserEmailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试与个人账号相关服务
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class AccountServiceTests {



    @Autowired
    private AccountServiceImp accountServiceImp;

    @Test
    public void test(){
        accountServiceImp.registerFromEmail("2502665955@qq.com","1111111","hduisa");
    }

//    //检查验证码
//    Boolean checkCode(String email, String newCode);
//
//    //该邮箱是否被其他用户绑定
//    boolean isUserEmailSingle(String email);
//
//    //该用户是否已经绑定了邮箱
//    boolean isUserEmailBound(Integer userId);
//
//    // 收件人 主题 内容
//    int sendSimpleMail(String to, String subject, String content);
//    @Autowired
//    private UserEmailServiceImpl userEmailService;
//
//    @Autowired
//    private UserCode userCode;
//
//    @Test
//    public void testCheckCode(){
//        String email =
//
//    }
//
//    @Test
//    public void testExamineAds(){
//        System.out.println(advertisementService.examineAds(1,1));
//    }
}
