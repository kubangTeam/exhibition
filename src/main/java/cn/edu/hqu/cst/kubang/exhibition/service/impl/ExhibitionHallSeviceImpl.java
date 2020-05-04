package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionHallDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.LocationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.City;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionHall;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExhibitionHallSeviceImpl  implements IExhibitionHallService {


    @Autowired
    private ExhibitionHall exhibitionHall;

    @Autowired
    private ExhibitionHallDao exhibitionHallDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private City city;


    @Override
    public Map<String ,String> findCityNameByExhibitionHallId(int exhibitionHallId) {
        exhibitionHall = exhibitionHallDao.selectExhibitionHallById(exhibitionHallId);
        String exhibitionName = exhibitionHall.getExhibitionName();
        int cityCode = exhibitionHall.getCityCode();
        String City = locationDao.getCityByCode(cityCode).getCity();
        Map<String ,String>map = new HashMap<>();
        map.put("exhibitionName",exhibitionName);
        map.put("CityName",City);
        return map;
    }
}
