package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.entity.City;
import cn.edu.hqu.cst.kubang.exhibition.entity.Province;

import java.util.List;

public interface ILocationService {
    // 获取所有省份信息
    List<Province> getAllProvince();

    // 获取所有市的信息
    List<City> getAllCities(Integer provinceCode);
}
