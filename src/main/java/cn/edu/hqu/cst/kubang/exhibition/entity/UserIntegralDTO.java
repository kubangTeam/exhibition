package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author: KongKongBaby
 * @create: 2020-04-29 13:36
 * @description: 适用于移动端的实体类
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserIntegralDTO implements Serializable {
    private Integer integral; //积分
    private Integer status; // 0 扣除积分 1增加积分
    private BigInteger time;
}
