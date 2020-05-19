package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: KongKongBaby
 * @create: 2020-05-19 10:52
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExhibitionGoodsDTO implements Serializable{
    private Integer goodsId;
    private String goodsName;
    private Integer categoryId;
    private String currentPrice;
    private String image;
}
