package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

@Component
public class CompanyNew {
    int userId;
    String name;
    String address;
    String website;
    String type;
    String tel;
    String introduce;
    String HeadPicture;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHeadPicture() {
        return HeadPicture;
    }

    public void setHeadPicture(String headPicture) {
        HeadPicture = headPicture;
    }

    @Override
    public String toString() {
        return "CompanyNew{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", type='" + type + '\'' +
                ", tel='" + tel + '\'' +
                ", introduce='" + introduce + '\'' +
                ", HeadPicture='" + HeadPicture + '\'' +
                '}';
    }
}
