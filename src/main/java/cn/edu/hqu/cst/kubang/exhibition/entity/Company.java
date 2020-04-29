package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
@Document(indexName = "company", type = "_doc", shards = 6, replicas = 3 )//索引，类型，分片，副本
public class Company implements Serializable {
    @Id
    private int id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Text)
    private String type;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Text)
    private String website;

    @Field(type = FieldType.Text)
    private String telephone;

    @Field(type = FieldType.Text)
    private String area;

    @Field(type = FieldType.Text)
    private String introduction;

    @Field(type = FieldType.Integer)
    private int identifyStatus;

    @Field(type = FieldType.Text)
    private String codeCertificateType;

    @Field(type = FieldType.Text)
    private String codeCertificateId;

    @Field(type = FieldType.Text)
    private String codeCertificatePic;

    @Field(type = FieldType.Text)
    private String responsiblePersonName;

    @Field(type = FieldType.Text)
    private String responsiblePersonIdcard;

    @Field(type = FieldType.Text)
    private String headPicture;
}
