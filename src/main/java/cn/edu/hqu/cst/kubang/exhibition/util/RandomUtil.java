package cn.edu.hqu.cst.kubang.exhibition.util;

import java.util.Random;

/**
 * @author: KongKongBaby
 * @create: 2020-05-01 14:41
 * @description: 生成随机值
 **/

public class RandomUtil {
    public static String randomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
