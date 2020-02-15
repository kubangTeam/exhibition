package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * author sunquan
 * Description 用户表mapper类
 * Date 2020.2.15
 */
@Mapper
@Repository
public interface UserInformationMapper {

    UserInformation selectById(int id);
    UserInformation selectByName(String username);
    UserInformation selectByEmail(String email);
    int insertUser(UserInformation user);
    //int updateStatus(int id, int status);
    int updateHeader(int id ,String headerUrl);
    int updatePassword(int id ,String password);
}
