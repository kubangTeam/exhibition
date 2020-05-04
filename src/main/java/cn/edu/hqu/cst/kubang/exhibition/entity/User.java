package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 13:49
 * @description:
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Component
public class User implements Serializable {
    private Integer userId;
    private String userAccount;
    private Integer userCompanyId; //用户所属公司，null表示个人
    private String userName;
    private String userPassword;
    private String userSex;
    private Integer userPermission; //用户权限。0——普通 1——管理员
    private Integer userIntegral; //用户积分
    private String userReccode;
    private String userPicture;
    private String userChooseCity; //用户选择的地点
    private String userEmail;
    private Integer userOrganizerId; //用户承办方id

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(Integer userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }

    public String getUserReccode() {
        return userReccode;
    }

    public void setUserReccode(String userReccode) {
        this.userReccode = userReccode;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserChooseCity() {
        return userChooseCity;
    }

    public void setUserChooseCity(String userChooseCity) {
        this.userChooseCity = userChooseCity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserOrganizerId() {
        return userOrganizerId;
    }

    public void setUserOrganizerId(Integer userOrganizerId) {
        this.userOrganizerId = userOrganizerId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", userCompanyId=" + userCompanyId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userPermission=" + userPermission +
                ", userIntegral=" + userIntegral +
                ", userReccode='" + userReccode + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userChooseCity='" + userChooseCity + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userOrganizerId=" + userOrganizerId +
                '}';
    }
}
