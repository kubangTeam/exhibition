package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 00:30
 * @Description:
 */
@Service
public class ExhibitionServiceImpl implements IExhibitionService {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Override
    public List<Exhibition> queryAllExhibitions() {
        return exhibitionDao.queryAllExhibitions();
    }

    @Override
    @NullDisable
    public Exhibition queryExhibitionByID(Integer id) {
        return exhibitionDao.queryExhibitionByID(id);
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionsByStatus(Integer status) {
        return exhibitionDao.queryExhibitionsByStatus(status);
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionsByKeyWord(String keyWord) {
        return exhibitionDao.queryExhibitionsByKeyWord(keyWord);
    }

    @Override
    public int saveExhibition(Exhibition exhibition) {
        //在数据库中 name start_time end_time exhibiton_hall_id status为非空字段
        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
                && StringUtils.isEmpty(exhibition.getExhibitionHallId()) && StringUtils.isEmpty(exhibition.getStatus())) {
            System.out.println("name start_time end_time exhibiton_hall_id status 为空");
            return -1;
        }
        int i = exhibitionDao.saveExhibition(exhibition);
        return i;
    }

    @Override
    public int modifyExhibition(Exhibition exhibition) {
        //在数据库中 name start_time end_time exhibiton_hall_id status为非空字段
        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
                && StringUtils.isEmpty(exhibition.getExhibitionHallId()) && StringUtils.isEmpty(exhibition.getStatus())) {
            System.out.println("name start_time end_time exhibiton_hall_id status 为空");
            return -1;
        }
        int i = exhibitionDao.modifyExhibition(exhibition);
        return i;
    }

    @Override
    @NullDisable
    public int deleteExhibition(Integer id) {
        return exhibitionDao.deleteExhibition(id);
    }
}
