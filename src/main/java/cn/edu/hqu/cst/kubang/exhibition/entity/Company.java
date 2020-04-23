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
}
