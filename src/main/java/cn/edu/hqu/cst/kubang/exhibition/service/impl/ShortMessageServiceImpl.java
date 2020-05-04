package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.UIDGenerator;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserCodeDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
import cn.edu.hqu.cst.kubang.exhibition.service.ICompanyService;
import cn.edu.hqu.cst.kubang.exhibition.service.IShortMessageService;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class ShortMessageServiceImpl implements IShortMessageService
{
    @Autowired
    private UserCodeDao userCodeDao;

    @Autowired
    private UserDao userDao;

    //对应阿里云账户的 accessKeyId
    @Value("LTAI4FsEudzSht2tKHgD6LCE")
    private String accessKeyId;

    //对应阿里云账户的 accessKeySecret
    @Value("VdJC8u8Pxd7XVqZw0hi4XtL74CegvD")
    private String accessKeySecret;

    //对应签名名称
    @Value("酷邦网")
    private String signName;

    //对应模板代码
    @Value("SMS_175574488")
    private String templateCode;

    @Override
    public Boolean checkCode(String phoneNumber, String newCode) {
        //数据库根据email获取对应的code和sendingTime
        UserCode userCode = userCodeDao.queryUserCodeByAccount(phoneNumber);
        Long sendingTime = Long.valueOf(userCode.getSendingTime());
        String oldCode = userCode.getCode();
        //计算时间差
        Long checkingTime = Calendar.getInstance().getTimeInMillis();
        Long minute = (checkingTime - sendingTime) / (1000 * 60);
        //30分钟内且验证码正确
        if (minute <= 30 && newCode.equals(oldCode)) {
            System.out.println("验证通过");
            System.out.println("时间差 = " + minute + " 正确的验证码 = " + oldCode + " 用户提供的验证码 = " + newCode);
            return true;
        } else {
            System.out.println("验证不通过");
            System.out.println("时间差 = " + minute + "分钟, 正确的验证码 = " + oldCode + " 用户提供的验证码 = " + newCode);
            return false;
        }
    }

    @Override
    public int sendShortMessage(String phoneNumber)
    {
        //产生随机验证码
        int verifyCode = (int) ((Math.random() * 9 + 1) * 100000);
        //String verifyCode = UIDGenerator.getUUID();

        DefaultProfile profile = DefaultProfile.getProfile("default",
                accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        //阿里云对应发送短信的服务器地址
        request.setDomain("dysmsapi.aliyuncs.com");
        //对应的版本号
        request.setVersion("2017-05-25");

        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\": "+ verifyCode + "}");
        //request.putQueryParameter("TemplateParam", verifyCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
            return  101;
        } catch (ClientException e) {
            e.printStackTrace();
            return  102;
        }
        return verifyCode;
    }

    @Override
    public int  saveUserCode(UserCode userCode) {

        int row = userCodeDao.saveUserCode(userCode);
        return row;
    }

    @Override
    public UserCode queryUserCodeByPhone(String phone) {

        return userCodeDao.queryUserCodeByAccount(phone);
    }

    @Override
    public boolean isUserPhoneSingle(String phoneNumber) {

        return userDao.GetUseInfoFromAccount(phoneNumber) == null;
    }
}