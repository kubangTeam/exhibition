package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参构造
public class UserInformation {
    private int userId;
    private String userAccount;
    private String userName;
    private String userPassword;
    private String userSex;
    private String userIntegral;
    private String userReccode;
    private String userPicture;
    private String userChooseCity;
    private String userEmail;
}
