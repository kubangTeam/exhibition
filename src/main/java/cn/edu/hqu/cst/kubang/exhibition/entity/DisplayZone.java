package cn.edu.hqu.cst.kubang.exhibition.entity;


import org.springframework.stereotype.Component;
/**
 *  @author: sunquan
 *  @Date: 2020.03.21 23:09
 *  @Description: 商品分类
 */
@Component
public class GoodsType {
    private int id;
    private String goodsType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", goodsType='" + goodsType + '\'' +
                '}';
    }
}
