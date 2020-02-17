package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.Data;

@Data
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


    @Override
    public String toString() {
        return "UserInforamtion{" +
                "userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userIntegral='" + userIntegral + '\'' +
                ", userReccode='" + userReccode + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userChooseCity='" + userChooseCity + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
