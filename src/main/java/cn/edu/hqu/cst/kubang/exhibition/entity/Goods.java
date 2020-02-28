package cn.edu.hqu.cst.kubang.exhibition.entity;

import java.util.Date;

/**
 * @Author SunChonggao
 * @Date 2020/2/16 7:28
 * @Version 1.0
 * @Description:展品实体类
 */
public class Goods {
    private int goodsId;//展品ID
    private String goodsName;//展品名
    private int categoryId;//类别ID
    private String goodsAreaNumber;//展位号
    private int companyId;//所属公司ID
    private String website;//网址
    private String originPlace;//原产地
    private String originalPrice;//原价格
    private String currentPrice;//现价格
    private String goodsIntroduce;//简介
    private Date startTime;//参展开始时间
    private Date endTime;//参展结束时间
    private int goodsStatus;//展品状态（未上线为0，已上线为1）
    private String keyword;//关键词
    private String identifyStatus;
    private String pic;//展品图片
    private int picId;//图片ID
    private int priority;//优先级

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

    public String getGoodIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodIntroduce(String goodIntroduce) {
        this.goodsIntroduce = goodIntroduce;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIdentifyStatus() {
        return identifyStatus;
    }

    public void setIdentifyStatus(String identifyStatus) {
        this.identifyStatus = identifyStatus;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
                ", goodIntroduce='" + goodsIntroduce + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", goodsStatus=" + goodsStatus +
                ", keyword='" + keyword + '\'' +
                ", identifyStatus='" + identifyStatus + '\'' +
                ", pic='" + pic + '\'' +
                ", picId=" + picId +
                ", priority=" + priority +
                '}';
    }
}