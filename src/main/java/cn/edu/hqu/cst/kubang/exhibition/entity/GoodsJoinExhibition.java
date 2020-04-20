package cn.edu.hqu.cst.kubang.exhibition.entity;


import org.springframework.stereotype.Component;

@Component
public class GoodsJoinExhibition {
    private int id;
    private int goodsId;
    private int exhibitionId;
    private int subareaId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getSubareaId() {
        return subareaId;
    }

    public void setSubareaId(int subareaId) {
        this.subareaId = subareaId;
    }


    @Override
    public String toString() {
        return "GoodsJoinExhibition{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", exhibitionId=" + exhibitionId +
                ", subareaId=" + subareaId +
                '}';
    }
}
