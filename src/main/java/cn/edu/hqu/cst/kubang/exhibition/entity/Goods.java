package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author SunChonggao
 * @Date 2020/2/16 7:28
 * @Version 1.0
 * @Description:展品实体类
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //满参构造
@Accessors(chain = true)
@Document(indexName = "goods", type = "_doc", shards = 6, replicas = 3 )//索引，类型，分片，副本
@Component
public class Goods implements Serializable {
    @Id
    private int goodsId;//展品ID

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    //存储用ik_max_word分词器，搜索用ik_smart分词器
    private String goodsName;//展品名

    @Field(type = FieldType.Integer)
    private int categoryId;//类别ID

    @Field(type = FieldType.Text)
    private String goodsAreaNumber;//展位号

    @Field(type = FieldType.Integer)
    private int companyId;//所属公司ID

    @Field(type = FieldType.Text)
    private String website;//展品网址

    @Field(type = FieldType.Text)
    private String originPlace;//原产地

    @Field(type = FieldType.Text)
    private String originalPrice;//原价格

    @Field(type = FieldType.Text)
    private String currentPrice;//现价格

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsIntroduce;//简介

    @Field(type = FieldType.Date)
    private Date startTime;//参展开始时间Date

    @Field(type = FieldType.Date)
    private Date endTime;//参展结束时间

    @Field(type = FieldType.Integer)
    private int goodsStatus;//展品状态（未上线为0，已上线为1,已删除为2）

    @Field(type = FieldType.Integer)
    private int priority;//优先级

    @Field(type = FieldType.Text)
    private String image;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsAreaNumber() {
        return goodsAreaNumber;
    }

    public void setGoodsAreaNumber(String goodsAreaNumber) {
        this.goodsAreaNumber = goodsAreaNumber;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", categoryId=" + categoryId +
                ", goodsAreaNumber='" + goodsAreaNumber + '\'' +
                ", companyId=" + companyId +
                ", website='" + website + '\'' +
                ", originPlace='" + originPlace + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", goodsIntroduce='" + goodsIntroduce + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", goodsStatus=" + goodsStatus +
                ", priority=" + priority +
                ", image='" + image + '\'' +
                '}';
    }
}
