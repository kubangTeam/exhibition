package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class Exhibition implements Serializable {
    private Integer id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer exhibitionHallId; //举办展馆id
    private String showRoom; //所用展厅
    private BigDecimal acreage;//展会面积
    private String trade; //所属行业
    private String organizer;//主办单位
    private Integer contractorId;//承办单位id
    private Integer session;//举办届数
    private String period;//举办周期
    private String introduction;//展会概况
    private String Tel;//联系方式
    // 0保存成功待上传 1上传成功待审核 2审核通过 3 审核未通过 4 假删除状态 5 过期
    private Integer status;
    private String picture;//展会图标url地址
}
