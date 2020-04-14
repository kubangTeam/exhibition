package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
/**
 *  @author: sunquan
 *  @Date: 2020.03.21 23:09
 *  @Description: 展会广告实体类
 */
@Component
public class Advertisement implements Serializable {
    private int id;
    private int companyId;
    private int organizerId;
    private Date startTime;
    private Date endTime;
    private int priority;
    private String picture;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", organizerId=" + organizerId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", priority=" + priority +
                ", picture='" + picture + '\'' +
                ", status=" + status +
                '}';
    }
}
