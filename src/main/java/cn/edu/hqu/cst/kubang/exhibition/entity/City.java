package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 19:13
 * @description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class City implements Serializable {
    private Integer code;
    private String city;
    private Integer provinceCode;
}
