package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInformationMapper {
    UserInformation selectById(int id);
    UserInformation selectByName(String username);
    UserInformation selectByEmail(String email);
    int insertUser(UserInformation user);
    //int updateStatus(int id, int status);
    int updateHeader(int id ,String headerUrl);
    int updatePassword(int id ,String password);
}
