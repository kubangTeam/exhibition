package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.LocationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.City;
import cn.edu.hqu.cst.kubang.exhibition.entity.Province;
import cn.edu.hqu.cst.kubang.exhibition.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: KongKongBaby
 * @create: 2020-04-27 19:24
 * @description: 位置服务 细节略过
 **/

@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<Province> getAllProvince() {
        List<Province> provinceList = locationDao.getAllProvince()
                .stream()
                .filter(p -> !p.getProvince().contains("澳门")
                && !p.getProvince().contains("香港")
                && !p.getProvince().contains("台湾"))
                .collect(Collectors.toList());
        Collections.sort(provinceList,(p1,p2)-> Collator.getInstance(Locale.CHINESE).compare(p1.getProvince(),p2.getProvince()));
        return provinceList;
    }

    @Override
    public List<City> getAllCities(Integer provinceCode) {
        if (null == provinceCode || provinceCode < 1)
            return null;
        List<City> allCities = locationDao.getAllCities(provinceCode);
        Collections.sort(allCities,(c1,c2)-> Collator.getInstance(Locale.CHINESE).compare(c1.getCity(),c2.getCity()));
        return allCities;
    }
}
