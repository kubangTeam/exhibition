package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionSubarea;

import java.util.List;
import java.util.Map;

public interface IOrganizerService {

    Map<String,Object> holdExhibition(Exhibition exhibition, ExhibitionSubarea exhibitionSubarea);
    List<Exhibition> getAllExhibitions(int organizerID);
}
