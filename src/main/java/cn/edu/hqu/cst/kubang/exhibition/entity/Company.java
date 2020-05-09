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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIdentifyStatus() {
        return identifyStatus;
    }

    public void setIdentifyStatus(int identifyStatus) {
        this.identifyStatus = identifyStatus;
    }

    public String getCodeCertificateType() {
        return codeCertificateType;
    }

    public void setCodeCertificateType(String codeCertificateType) {
        this.codeCertificateType = codeCertificateType;
    }

    public String getCodeCertificateId() {
        return codeCertificateId;
    }

    public void setCodeCertificateId(String codeCertificateId) {
        this.codeCertificateId = codeCertificateId;
    }

    public String getCodeCertificatePic() {
        return codeCertificatePic;
    }

    public void setCodeCertificatePic(String codeCertificatePic) {
        this.codeCertificatePic = codeCertificatePic;
    }

    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    public String getResponsiblePersonIdcard() {
        return responsiblePersonIdcard;
    }

    public void setResponsiblePersonIdcard(String responsiblePersonIdcard) {
        this.responsiblePersonIdcard = responsiblePersonIdcard;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", telephone='" + telephone + '\'' +
                ", area='" + area + '\'' +
                ", introduction='" + introduction + '\'' +
                ", identifyStatus=" + identifyStatus +
                ", codeCertificateType='" + codeCertificateType + '\'' +
                ", codeCertificateId='" + codeCertificateId + '\'' +
                ", codeCertificatePic='" + codeCertificatePic + '\'' +
                ", responsiblePersonName='" + responsiblePersonName + '\'' +
                ", responsiblePersonIdcard='" + responsiblePersonIdcard + '\'' +
                ", headPicture='" + headPicture + '\'' +
                '}';
    }
}
