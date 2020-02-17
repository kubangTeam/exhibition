package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  @author: 邢佳成
 *  @Date: 2020.02.17 21:49
 *  @Description:
 */
@Data
public class Exhibition implements Serializable {
    private Integer id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer exhibitionHallId;
    private String showRoom;
    private BigDecimal acreage;//面积
    private String trade;
    private String organizer;
    private Integer contractorId;
    private Integer session;
    private String period;
    private String introduction;
    private String Tel;
    private Integer status; // 0保存成功待上传 1上传成功待审核 2审核通过 3 审核未通过 4 假删除状态 5 过期
    private String picture;

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", exhibitionHallId=" + exhibitionHallId +
                ", showRoom='" + showRoom + '\'' +
                ", acreage=" + acreage +
                ", trade='" + trade + '\'' +
                ", organizer='" + organizer + '\'' +
                ", contractorId=" + contractorId +
                ", session=" + session +
                ", period='" + period + '\'' +
                ", introduction='" + introduction + '\'' +
                ", Tel='" + Tel + '\'' +
                ", status='" + status + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
