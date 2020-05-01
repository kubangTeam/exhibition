package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @Author SunChonggao
 * @Date 2020/5/1 10:36
 * @Version 1.0
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Search {
    private int id;
    private int userId;
    private String searchEntry;
    private Timestamp searchTime;
    private int classification;
}
