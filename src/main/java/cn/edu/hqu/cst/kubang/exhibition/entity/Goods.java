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
import java.util.Date;

/**
 * @Author SunChonggao
 * @Date 2020/2/16 7:28
 * @Version 1.0
 * @Description:展品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "goods", type = "_doc", shards = 6, replicas = 3 )//索引，类型，分片，副本
public class Goods implements Serializable{
    @Id
    private int goodsId;//展品ID

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    //存储用ik_max_word分词器，搜索用ik_smart分词器
    private String goodsName;//展品名

    @Field(type = FieldType.Integer)
    private int categoryId;//类别ID

    @Field(type = FieldType.Text)
    private String goodsAreaNumber;//展位号

    @Field(type = FieldType.Integer)
    private int companyId;//所属公司ID

    @Field(type = FieldType.Text)
    private String website;//展品网址

    @Field(type = FieldType.Text)
    private String originPlace;//原产地

    @Field(type = FieldType.Text)
    private String originalPrice;//原价格

    @Field(type = FieldType.Text)
    private String currentPrice;//现价格

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsIntroduce;//简介

    @Field(type = FieldType.Date)
    private Date startTime;//参展开始时间Date

    @Field(type = FieldType.Date)
    private Date endTime;//参展结束时间

    @Field(type = FieldType.Integer)
    private int goodsStatus;//展品状态（未上线为0，已上线为1,已删除为2）

    @Field(type = FieldType.Integer)
    private int priority;//优先级

    @Field(type = FieldType.Text)
    private  String image;
}