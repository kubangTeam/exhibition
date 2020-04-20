package cn.edu.hqu.cst.kubang.exhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class Company implements Serializable {
    private int id;
    private String name;
    private String type;
    private String address;
    private String website;
    private String telephone;
    private String area;
    private String introduction;
    private int identifyStatus;
    private String codeCertificateType;
    private String codeCertificateId;
    private String codeCertificatePic;
    private String responsiblePersonName;
    private String responsiblePersonIdcard;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", telephone='" + telephone + '\'' +
                ", area='" + area + '\'' +
                ", introduction='" + introduction + '\'' +
                ", identifyStatus='" + identifyStatus + '\'' +
                ", codeCertificateType='" + codeCertificateType + '\'' +
                ", codeCertificateId='" + codeCertificateId + '\'' +
                ", codeCertificatePic='" + codeCertificatePic + '\'' +
                ", responsiblePersonName='" + responsiblePersonName + '\'' +
                ", responsiblePersonIdcard='" + responsiblePersonIdcard + '\'' +
                ", headPicture='" + headPicture + '\'' +
                '}';
    }
}
