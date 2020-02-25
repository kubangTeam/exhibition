package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserEmailDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserSMSDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserCode;
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
import java.util.Date;

@Service
public class ShortMessageServiceImpl implements IShortMessageService
{
    @Autowired
    private UserSMSDao SMSDao;

    @Autowired
    private UserInformationDao userDao;

    //对应阿里云账户的 accessKeyId
    @Value("${SMS.accessKeyId}")
    private String accessKeyId;

    //对应阿里云账户的 accessKeySecret
    @Value("${SMS.accessKeySecret}")
    private String accessKeySecret;

    //对应签名名称
    @Value("${SMS.signName}")
    private String signName;

    //对应模板代码
    @Value("${SMS.templateCode}")
    private String templateCode;

    @Override
    public Boolean checkCode(String phoneNumber, String newCode) {
        //数据库根据email获取对应的code和sendingTime
        UserCode userCode = SMSDao.queryUserCodeByAccount(phoneNumber);
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
        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
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
    public void saveUserCode(UserCode userCode) {
       SMSDao.saveUserCode(userCode);
    }

    @Override
    public UserCode queryUserCodeByEmail(String phone) {
        return SMSDao.queryUserCodeByAccount(phone);
    }
}
