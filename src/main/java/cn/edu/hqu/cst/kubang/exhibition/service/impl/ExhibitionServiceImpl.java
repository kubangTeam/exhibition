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
 *  @author: 邢佳成
 *  @Date: 2020.02.18 00:30
 *  @Description: 关于查询如果id=-1表示查找结果不存在
 */
@Service
public class ExhibitionServiceImpl implements IExhibitionService {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Override
    @NullDisable
    public Exhibition queryExhibitionByID(Integer id) {
        Exhibition exhibition = exhibitionDao.queryExhibitionByID(id);
        if (StringUtils.isEmpty(exhibition)){
            exhibition.setId(-1);
        }
        return exhibition;
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionByStatus(Integer status) {
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionByStatus(status);
        if (null == exhibitionList && exhibitionList.isEmpty()){
            Exhibition exhibition = new Exhibition();
            exhibition.setId(-1);
            exhibitionList.add(exhibition);
        }
        return exhibitionList;
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionByKeyWord(String keyWord) {
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionByKeyWord(keyWord);
        if (null == exhibitionList && exhibitionList.isEmpty()){
            Exhibition exhibition = new Exhibition();
            exhibition.setId(-1);
            exhibitionList.add(exhibition);
        }
        return exhibitionList;
    }

    @Override
    public int saveExhibition(Exhibition exhibition) {
        return 0;
    }

    @Override
    public int modifyExhibition(Exhibition exhibition) {
        return 0;
    }

    @Override
    public int deleteExhibition(Integer id) {
        return 0;
    }
}
