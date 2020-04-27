package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserInfoDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.ResponseJson;
import cn.edu.hqu.cst.kubang.exhibition.entity.User;
import cn.edu.hqu.cst.kubang.exhibition.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Object getUserInfo(String account, String password) {
        if (null == account || null == password || account == "" || password == "")
            return new ResponseJson(false, "-002", "参数为空", null);
        User user = userInfoDao.queryUserInfoByAccount(account);
        if (password.equals(user.getUserPassword())) {
            return new ResponseJson(true, "017", "登录成功", user);
        } else
            return new ResponseJson(false, "003", "密码错误", null);
    }

    @Override
    public Object changePhoto(Integer id, String photo) {
        return null;
    }

    @Override
    public Object changeName(Integer id, String name) {
        if (id == null || name == null || name == "" || id < 0)
            return new ResponseJson(false, "-002", "参数为空", null);
        Integer changeRow = userInfoDao.changeUserName(id, name);
        if (changeRow == 1)
            return new ResponseJson(true, "005", "修改名字成功", null);
        else
            return new ResponseJson(false, "-004", "数据库异常", null);
    }

    @Override
    public Object changeEmail(Integer id, String email) {
        return null;
    }

    @Override
    public Object changePass(Integer id, String newPassword, String oldPassword) {
        if (id == null || newPassword == null || newPassword == "" || null == oldPassword || oldPassword == ""  || id < 0)
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
}
