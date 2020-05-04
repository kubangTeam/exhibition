package cn.edu.hqu.cst.kubang.exhibition.ServiceTests;

import cn.edu.hqu.cst.kubang.exhibition.ExhibitionApplication;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExhibitionApplication.class)
public class ShortMessageServiceTests {

    @Autowired
    private UserCode userCode;

    @Autowired
    private IShortMessageService iShortMessageService;

    @Test
    public void testCheckCode(){

    }


    @Test
    public void testSendShortMessage(){
        String phoneNumber = "17338719294";
        int verifyCode = iShortMessageService.sendShortMessage(phoneNumber);
        System.out.println(verifyCode);

        //测试保存
        userCode.setAccount(phoneNumber);
        userCode.setCode(String.valueOf(verifyCode));
        iShortMessageService.saveUserCode(userCode);

    }


}
