package cn.edu.hqu.cst.kubang.exhibition.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository

//对于客户端，在用户登录以后，要一直保持一个session，这里用一个元组(userId,sessionID)来记录
//当不同的session申请登录同一个账号，则要替换 对应记录，以便实现单点登录
//当一个新的session申请登录一个账号，则要添加记录

public interface UserSessionDao {
    String queryByUserID(int userID);
    int queryBySessionId(String sessionID);

    int containsSessionId(String sessionID);
    void add(int userID, String sessionID);
    void removeByUserId(int userID);
}
