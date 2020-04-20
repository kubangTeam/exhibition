package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @Author SunChonggao
 * @Date 2020/4/14 12:07
 * @Version 1.0
 * @Description:展品图片实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class GoodsPic {
    int picId;
    int goodsId;
    String pic;

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "GoodsPic{" +
                "picId=" + picId +
                ", goodsId=" + goodsId +
                ", pic='" + pic + '\'' +
                '}';
    }
}
