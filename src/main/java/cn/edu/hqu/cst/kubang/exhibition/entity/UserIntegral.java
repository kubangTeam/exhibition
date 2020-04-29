package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author: KongKongBaby
 * @create: 2020-04-29 12:47
 * @description: 用户积分
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserIntegral implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer integral; //积分
    private Integer status; // 0 扣除积分 1增加积分
    private String message; // 附言，比如兑换了什么东西 扣除50积分
    private BigInteger time;
}
