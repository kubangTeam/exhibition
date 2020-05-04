package cn.edu.hqu.cst.kubang.exhibition.service;

import java.util.Map;

public interface IExhibitionHallService {
    Map<String,String> findCityNameByExhibitionHallId(int exhibitionHallId);
}
