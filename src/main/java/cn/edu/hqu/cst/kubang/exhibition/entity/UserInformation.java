package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor//满参构造
public class UserInformation implements Serializable {
    private Integer userId;
    private String userAccount;
    private Integer userCompanyId;
    private String userName;
    private String userPassword;
    private String userSex;
    private Integer userPermission;
    private Integer userIntegral;
    private String userReccode;
    private String userPicture;
    private String userChooseCity;
    private String userEmail;
}
