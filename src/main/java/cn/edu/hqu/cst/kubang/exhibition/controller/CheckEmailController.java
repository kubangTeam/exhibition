package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.service.IBindUserEmailService;
import cn.edu.hqu.cst.kubang.exhibition.service.ICheckCodeService;
import cn.edu.hqu.cst.kubang.exhibition.service.ICheckEmailService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/email")
public class CheckEmailController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICheckCodeService checkCodeService;

    @Autowired
    private ICheckEmailService checkEmailService;

    @Autowired
    private IBindUserEmailService bindUserEmailService;

    //前端必须是表单数据,如果是其他比如json格式则会报错
    @PostMapping("/check")
    @ResponseBody
    public String checkCode(@RequestParam("userId") Integer userId,@RequestParam("email") String email,@RequestParam("newCode") String newCode) {
        System.out.println("checkCode: ");
        System.out.println("userId: "+userId);
        System.out.println("email: "+email);
        System.out.println("newCode: "+newCode);
        Boolean res = checkCodeService.checkCode(email, newCode);
        if (res) {
            //验证码检查通过 接着检查邮箱是否已被绑定
            boolean userEmailSingle = checkEmailService.isUserEmailSingle(email);
            if (userEmailSingle) {
                //验证通过  将该邮箱存进数据库 与该用户绑定
                int status = bindUserEmailService.bindUserEmail(userId, email);
                if (status > 0) {
                    return "验证通过！";
                } else {
                    logger.error("绑定用户邮箱出现错误");
                    return "绑定用户邮箱出现错误";
                }
            } else {
                return "该邮箱已被绑定！";
            }
        } else {
            return "验证码错误！";
        }
    }
}
