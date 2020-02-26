package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserInformationDao userInformationDao;

    @Value("${pagehelper.pageSize}")
    private int pageSize;//一页显示几个，默认为10个

    //查询所有展会 不包括删除
    @Override
    @NullDisable
    public PageInfo<Exhibition> queryAllExhibitions(int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionDao.queryAllExhibitions();
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据 id查询 不包括删除
    @Override
    @NullDisable
    public Exhibition queryExhibitionByID(Integer id) {
        return exhibitionDao.queryExhibitionByID(id);
    }

    //根据 状态查询所有 不包括删除
    @Override
    @NullDisable
    public PageInfo<Exhibition> queryExhibitionsByStatus(Integer status, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByStatus(status);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据 关键词查询所有  不包括删除
    @Override
    @NullDisable
    public PageInfo<Exhibition> queryExhibitionsByKeyWord(String keyWord, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByKeyWord(keyWord.split(" "));
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }

    //根据状态和关键词查询 比如查找未通过审核的关键词为“1”的所有展品 不包括已删除
    @Override
    @NullDisable
    public PageInfo<Exhibition> queryExhibitionsByStatusAndKeyWord(String keyWord, int pageNum, Integer... status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> keyList = exhibitionDao.queryExhibitionsByKeyWord(keyWord.split(" "));
        PageInfo<Exhibition> pageInfo = new PageInfo<>(keyList);
        return pageInfo;
    }

    @Override
    public int saveExhibition(Exhibition exhibition) {
        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
                && StringUtils.isEmpty(exhibition.getExhibitionHallId())) {
            System.out.println("name start_time end_time exhibiton_hall_id 不允许为空");
            return -1;
        }
        exhibition.setStatus(0);
        int i = exhibitionDao.saveExhibition(exhibition);
        return i;
    }

    @Override
    public int modifyExhibition(Exhibition exhibition, Integer userId) {
        //在数据库中 name start_time end_time exhibiton_hall_id status为非空字段
        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
                && StringUtils.isEmpty(exhibition.getExhibitionHallId()) && null == userId) {
            System.out.println("name start_time end_time exhibiton_hall_id userId不允许为空");
            return -1;
        }
        /**前端根据用户的性质，   如果是普通用户，那么只有在0状态才可以修改，修改界面不显示status，后台得到的结果是null
         如果是管理员，那么可以修改任何状态，修改界面显示status且必须填写，后台得到的结果不是null
         这样根据是不是null来判断是不是管理员，为了确保正确性，可以进行双重判断
         */
        if (null == exhibition.getStatus() || userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
            exhibition.setStatus(0);
        return exhibitionDao.modifyExhibition(exhibition);
    }

    @Override
    @NullDisable
    public int modifyExhibitionStatus(Integer id, Integer userId, Integer status) {
        if (userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
            return -1;//权限不足
        return exhibitionDao.modifyExhibitionStatus(id, status);
    }

    @Override
    @NullDisable
    public int deleteExhibition(Integer id, Integer userId) {
        if (exhibitionDao.queryExhibitionByID(id).getStatus() != 0 && userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
            return -1;//权限不足
        return exhibitionDao.deleteExhibition(id);
    }

    //根据用户id查询他的公司的展品
    @Override
    @NullDisable
    public PageInfo<Exhibition> queryAllExhibitionsByUserId(Integer userId, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByUserId(userId);
        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
        return pageInfo;
    }
}
