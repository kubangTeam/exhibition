package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.City;
import cn.edu.hqu.cst.kubang.exhibition.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LocationDao {

    // 获取所有的省份信息
    List<Province> getAllProvince();

    // 获取所有市的信息
    List<City> getAllCities(Integer provinceCode);

    City getCityByCode(int code);


}
