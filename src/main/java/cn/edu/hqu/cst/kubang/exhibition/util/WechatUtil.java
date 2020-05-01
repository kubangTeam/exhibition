package cn.edu.hqu.cst.kubang.exhibition.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * @author: KongKongBaby
 * @create: 2020-05-01 14:28
 * @description: 微信相关的工具类
 **/

public class WechatUtil {
    public static String getOpenId(String code){
        String appId = "wx09e4f45a966f4273";
        String secret = "0d10a4a9b6a70e106c5c8d93f01f5198";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;
        String resultString = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        String openId = (String) jsonObject.get("openid");
        return openId;
    }
}
