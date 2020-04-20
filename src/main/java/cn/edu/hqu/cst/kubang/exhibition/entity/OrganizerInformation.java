package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

/**
 * 承办方账号实体类
 */
@Component
public class OrganizerInformation {
    int id;
    String organizer;
    String codeCertificateType;
    String codeCertificateId;
    String codeCertificatePic;
    String responsiblePersonName;
    String responsiblePersonIdcard;
    String responsiblePersonpic1;
    String responsiblePersonpic2;
    String responsiblePersonTel;
    String headPicture;
    String identifyStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
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

    public String getResponsiblePersonpic1() {
        return responsiblePersonpic1;
    }

    public void setResponsiblePersonpic1(String responsiblePersonpic1) {
        this.responsiblePersonpic1 = responsiblePersonpic1;
    }

    public String getResponsiblePersonpic2() {
        return responsiblePersonpic2;
    }

    public void setResponsiblePersonpic2(String responsiblePersonpic2) {
        this.responsiblePersonpic2 = responsiblePersonpic2;
    }

    public String getResponsiblePersonTel() {
        return responsiblePersonTel;
    }

    public void setResponsiblePersonTel(String responsiblePersonTel) {
        this.responsiblePersonTel = responsiblePersonTel;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getIdentifyStatus() {
        return identifyStatus;
    }

    public void setIdentifyStatus(String identifyStatus) {
        this.identifyStatus = identifyStatus;
    }

    @Override
    public String toString() {
        return "OrganizerInformation{" +
                "id=" + id +
                ", organizer='" + organizer + '\'' +
                ", codeCertificateType='" + codeCertificateType + '\'' +
                ", codeCertificateId='" + codeCertificateId + '\'' +
                ", codeCertificatePic='" + codeCertificatePic + '\'' +
                ", responsiblePersonName='" + responsiblePersonName + '\'' +
                ", responsiblePersonIdcard='" + responsiblePersonIdcard + '\'' +
                ", responsiblePersonpic1='" + responsiblePersonpic1 + '\'' +
                ", responsiblePersonpic2='" + responsiblePersonpic2 + '\'' +
                ", responsiblePersonTel='" + responsiblePersonTel + '\'' +
                ", headPicture='" + headPicture + '\'' +
                ", identifyStatus='" + identifyStatus + '\'' +
                '}';
    }
}
