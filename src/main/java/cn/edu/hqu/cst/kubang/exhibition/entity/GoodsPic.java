package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author SunChonggao
 * @Date 2020/4/14 12:07
 * @Version 1.0
 * @Description:展品图片实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsPic {
    int picId;
    int goodsId;
    String pic;
}
