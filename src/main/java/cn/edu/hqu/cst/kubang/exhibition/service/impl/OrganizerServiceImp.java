package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.OrganizerInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;
import cn.edu.hqu.cst.kubang.exhibition.service.IOrganizerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class OrganizerServiceImp implements IOrganizerService {


    @Autowired
    private ExhibitionDao exhibitionDao;
    @Override
    public Map<String, Object> holdExhibition(Exhibition exhibition, ExhibitionSubarea exhibitionSubarea) {
        return null;
    }

    @Override
    public List<Exhibition> getAllExhibitions(int organizerID) {
        return exhibitionDao.getExhibitionsByOrganizerID(organizerID);
    }
}
