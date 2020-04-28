package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
}
