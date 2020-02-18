package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    // 0保存成功待上传 1上传成功待审核 2审核通过 3 审核未通过 4 假删除状态 5 过期
    private Integer status;
    private String picture;
}
