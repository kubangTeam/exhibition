package cn.edu.hqu.cst.kubang.exhibition.dao;

import cn.edu.hqu.cst.kubang.exhibition.entity.Search;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author SunChonggao
 * @Date 2020/5/1 10:30
 * @Version 1.0
 * @Description:
 */
@Mapper
@Repository
public interface SearchDao {

    @Insert("insert into usersearch (userId, searchEntry, searchTime, classification) " +
            "value(#{userId},#{searchEntry},#{searchTime},#{classification})")
    int insertSearchRecord(Search search);

    @Select("select * from usersearch where id = #{id}")
    Search selectById(int id);

    @Select("select * from usersearch where userId = #{userId}")
    List<Search> selectByUserId(int userId);

}
