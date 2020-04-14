package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
}
