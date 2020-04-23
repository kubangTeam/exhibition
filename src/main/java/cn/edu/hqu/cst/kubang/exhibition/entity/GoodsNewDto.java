package cn.edu.hqu.cst.kubang.exhibition.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsNewDto implements Serializable {
    private int id;
    private String name;
    private String introduction;
    private String areaNumber;
    private String website;
    private Integer companyId;
    private String companyName;
    private List<String> picture;
    // 可能需要补充的属性 UI上没有
    private String originalPrice;
    private String currentPrice;
    private String originPlace;//原产地
    private String category;
    private Date startTime;
    private Date endTime;
}