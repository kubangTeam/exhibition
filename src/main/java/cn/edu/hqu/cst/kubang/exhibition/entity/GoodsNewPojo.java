package cn.edu.hqu.cst.kubang.exhibition.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsNewPojo implements Serializable {
    private int goodsId;
    private String goodsName;//展品名
    private int categoryId;//类别ID
    private String goodsAreaNumber;//展位号
    private int companyId;//所属公司ID
    private String website;//展品网址
    private String originPlace;//原产地
    private String originalPrice;//原价格
    private String currentPrice;//现价格
    private String goodsIntroduce;//简介
    private Date startTime;//参展开始时间Date
    private Date endTime;//参展结束时间
    private int goodsStatus;//展品状态（未上线为0，已上线为1,已删除为2）
    private int priority;//优先级
}
