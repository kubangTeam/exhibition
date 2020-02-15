package cn.edu.hqu.cst.kubang.exhibition.entity;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public String getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(String userIntegral) {
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
