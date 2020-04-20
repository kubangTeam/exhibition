package cn.edu.hqu.cst.kubang.exhibition.entity;

import org.springframework.stereotype.Component;

/**
 * 展会的二级分类
 */
@Component
public class ExhibitionSubarea {
    private int id;
    private int exhibitionId;
    private String subarea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }

    @Override
    public String toString() {
        return "ExhibitionSubarea{" +
                "id=" + id +
                ", exhibitionId=" + exhibitionId +
                ", subarea='" + subarea + '\'' +
                '}';
    }
}
