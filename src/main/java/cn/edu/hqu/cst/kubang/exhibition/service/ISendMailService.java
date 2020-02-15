package cn.edu.hqu.cst.kubang.exhibition.service;

/**
 * @Author: 邢佳成
 * @Description: 封装一个发邮件的接口，后边直接调用即可
 * @Date: 2020/02/13 12:57
 */
public interface ISendMailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    int sendSimpleMail(String to, String subject, String content);

}