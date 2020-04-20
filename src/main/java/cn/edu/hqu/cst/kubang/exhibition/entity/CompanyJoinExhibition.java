package cn.edu.hqu.cst.kubang.exhibition.entity;


import org.springframework.stereotype.Component;

@Component
public class CompanyJoinExhibition {
    private int id;
    private int companyId;
    private int exhibitionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    @Override
    public String toString() {
        return "CompanyJoinExhibition{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", exhibitionId=" + exhibitionId +
                '}';
    }
}
