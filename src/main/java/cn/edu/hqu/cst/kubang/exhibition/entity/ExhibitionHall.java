package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

@Component
public class ExhibitionHall {
    int id;
    String exhibitionName;
    int cityCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public void setExhibitionName(String exhibitionName) {
        this.exhibitionName = exhibitionName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "ExhibitionHall{" +
                "id=" + id +
                ", exhibitionName='" + exhibitionName + '\'' +
                ", cityCode=" + cityCode +
                '}';
    }
}
