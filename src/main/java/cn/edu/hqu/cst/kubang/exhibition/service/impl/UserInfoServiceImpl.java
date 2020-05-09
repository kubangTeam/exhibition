package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInfoDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserInfoService;
import cn.edu.hqu.cst.kubang.exhibition.util.ConvertBean;
import cn.edu.hqu.cst.kubang.exhibition.util.RandomUtil;
import cn.edu.hqu.cst.kubang.exhibition.util.WechatUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 14:19
 * @description: 移动端——用户资料
 * 注： 先完成主体功能 细节部分暂时不写
 **/

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserCodeDao userCodeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public ResponseJson<User> getUserInfo(String account, String password) {
        if (null == account || null == password || account == "" || password == "")
            return new ResponseJson(false, "-002", "参数为空", null);
        User user = userInfoDao.queryUserInfoByAccount(account);
        if (password.equals(user.getUserPassword())) {
            user.setUserPassword(null);
            return new ResponseJson(true, "017", "登录成功", user);
        } else
            return new ResponseJson(false, "003", "密码错误", null);
    }

    @Override
    public boolean isAccountExist(String account){
        User user = userInfoDao.queryUserInfoByAccount(account);
        if(user == null)
            return false;
        else
            return true;
    }

    @Override
    public ResponseJson changePhoto(Integer id, String photo) {
        if (id == null || photo == null || photo == "" || id < 0)
            return new ResponseJson(false, "-002", "参数为空", null);
        if (userInfoDao.changeUserPhoto(id, photo) > 0)
            return new ResponseJson(true, "005", "头像设置成功", null);
        else
            return new ResponseJson(false, "-004", "数据库异常", null);
    }

    @Override
    public ResponseJson changeName(Integer id, String name) {
        if (id == null || name == null || name == "" || id < 0)
            return new ResponseJson(false, "-002", "参数为空", null);
        Integer changeRow = userInfoDao.changeUserName(id, name);
        if (changeRow == 1)
            return new ResponseJson(true, "005", "修改名字成功", null);
        else
            return new ResponseJson(false, "-004", "数据库异常", null);
    }

    @Override
    public ResponseJson changeEmail(Integer id, String email) {
        return null;
    }

    @Override
    public ResponseJson changePass(Integer id, String newPassword, String oldPassword) {
        if (id == null || newPassword == null || newPassword == "" || null == oldPassword || oldPassword == "" || id < 0)
            return new ResponseJson(false, "-002", "参数为空", null);
        String originPass = userInfoDao.queryUserInfoById(id).getUserPassword();
        if (!originPass.equals(oldPassword))
            return new ResponseJson(false, "025", "密码错误", null);
        Integer changeRow = userInfoDao.changeUserPass(id, newPassword);
        if (changeRow == 1)
            return new ResponseJson(true, "020", "修改密码成功", null);
        else
            return new ResponseJson(false, "025", "密码错误", null);
    }

    @Override
    public ResponseJson queryUserIntegral(Integer id) {
        if (id == null || id < 0) {
            return new ResponseJson(false, "024", "参数不合法", null);
        }
        List<UserIntegral> userIntegralList = userInfoDao.queryUserIntegral(id);
        List<UserIntegralDTO> res = new ArrayList<>();
        userIntegralList.forEach(item -> res.add(ConvertBean.pojoToDto(item)));
        return new ResponseJson(true, "005", "操作成功", res);
    }

    @Override
    public ResponseJson resetPassword(String userAccount, String code, String newPassword) {
        if (userAccount == null || StringUtils.isEmpty(code) || StringUtils.isEmpty(newPassword))
            return new ResponseJson(false, "024", "参数不合法", null);
        /*HttpSession session = request.getSession();
        String key = "messageCodeUserID" + id;
        // 因为session有过期时间，所以不进行时间差比较
        String messageCode = (String) session.getAttribute(key);*/
        User user = userDao.GetUseInfoFromAccount(userAccount);
        UserCode userCode = userCodeDao.queryUserCodeByAccount(userAccount);
        String messageCode = userCode.getCode();
        if (!code.equals(messageCode)) {
            return new ResponseJson(false, "025", "验证码错误", null);
        } else {
            Integer changeRow = userInfoDao.resetUserPassword(user.getUserId(), newPassword);
            if (changeRow == 1) {
                return new ResponseJson(true, "005", "操作成功", null);
            } else {
                return new ResponseJson(false, "-004", "数据库异常", null);
            }
        }
    }

    @Override
    public ResponseJson wxLoginIn(String code) {
        if (StringUtils.isEmpty(code))
            return new ResponseJson(false, "-002", "参数为空", null);
        String openId = WechatUtil.getOpenId(code);
        System.out.println("openId: " + openId);
        User user = userInfoDao.queryUserInfoByAccount(openId);
        System.out.println(user);
        if (null == user) {
            String userName = "用户" + RandomUtil.randomString(6);
            String userReccode = RandomUtil.randomString(6);
            user = new User(null, openId, null, userName, openId, "未设置", 0, 0, userReccode, null, null, null, null);
            Integer changeRow = userInfoDao.saveUser(user);
            Integer userId = user.getUserId();
            System.out.println(userId);
            if (changeRow != 1 || userId == null || userId < 0) {
                return new ResponseJson(false, "-004", "数据库异常", null);
            }
            User user1 = userInfoDao.queryUserInfoById(userId);
            user = user1;
        }
        user.setUserPassword(null);
        return new ResponseJson(true, "025", "登录成功", user);
    }


}
