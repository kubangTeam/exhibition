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
import java.math.BigDecimal;
import java.util.Date;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 21:49
 *  @Description:
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //满参构造
@Accessors(chain = true)
@Document(indexName = "exhibition", type = "_doc", shards = 6, replicas = 3 )//索引，类型，分片，副本
@Component
public class Exhibition implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    //存储用ik_max_word分词器，搜索用ik_smart分词器
    private String name;

    @Field(type = FieldType.Date)
    private Date startTime;

    @Field(type = FieldType.Date)
    private Date endTime;

    @Field(type = FieldType.Integer)
    private Integer exhibitionHallId; //举办展馆id

    @Field(type = FieldType.Text)
    private String showRoom; //所用展厅

    @Field(type = FieldType.Text)
    private String acreage;//展会面积

    @Field(type = FieldType.Text)
    private String trade; //所属行业

    @Field(type = FieldType.Text)
    private String organizer;//主办单位

    @Field(type = FieldType.Integer)
    private Integer contractorId;//承办单位id

    @Field(type = FieldType.Integer)
    private Integer session;//举办届数

    @Field(type = FieldType.Text)
    private String period;//举办周期

    @Field(type = FieldType.Text)
    private String introduction;//展会概况

    @Field(type = FieldType.Text)
    private String Tel;//联系方式
    // 0保存成功待上传 1上传成功待审核 2审核通过 3 审核未通过 4 假删除状态 5 过期

    @Field(type = FieldType.Integer)
    private Integer status;

    @Field(type = FieldType.Text)
    private String picture;//展会图标url地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getExhibitionHallId() {
        return exhibitionHallId;
    }

    public void setExhibitionHallId(Integer exhibitionHallId) {
        this.exhibitionHallId = exhibitionHallId;
    }

    public String getShowRoom() {
        return showRoom;
    }

    public void setShowRoom(String showRoom) {
        this.showRoom = showRoom;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String  organizer) {
        this.organizer = organizer;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", exhibitionHallId=" + exhibitionHallId +
                ", showRoom='" + showRoom + '\'' +
                ", acreage='" + acreage + '\'' +
                ", trade='" + trade + '\'' +
                ", organizer='" + organizer + '\'' +
                ", contractorId=" + contractorId +
                ", session=" + session +
                ", period='" + period + '\'' +
                ", introduction='" + introduction + '\'' +
                ", Tel='" + Tel + '\'' +
                ", status=" + status +
                ", picture='" + picture + '\'' +
                '}';
    }
}
