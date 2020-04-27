package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 19:17
 * @description:
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Province implements Serializable {
    private Integer code;
    private String province;
}
