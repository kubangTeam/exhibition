package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationMapper;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    private UserInformationMapper userInformationDao;

    @Override
    public List<Exhibition> queryAllExhibitions() {
        List<Exhibition> exhibitionList = exhibitionDao.queryAllExhibitions();
        if (null != exhibitionList) {
            exhibitionList.forEach(item -> {
                if (item.getStatus() == 4)
                    exhibitionList.remove(item);
            });
        }
        return exhibitionList;
    }

    @Override
    @NullDisable
    public Exhibition queryExhibitionByID(Integer id) {
        Exhibition exhibition = exhibitionDao.queryExhibitionByID(id);
        if (exhibition.getStatus() == 4)
            exhibition = null;
        return exhibition;
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionsByStatus(Integer status) {
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByStatus(status);
        if (null != exhibitionList) {
            exhibitionList.forEach(e -> {
                if (e.getStatus() == 4)
                    exhibitionList.remove(e);
            });
        }
        return exhibitionList;
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionsByKeyWord(String keyWord) {
//        System.out.println(exhibitionDao.queryExhibitionsByKeyWord(keyWord));
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByKeyWord(keyWord);
        if (null != exhibitionList) {
            exhibitionList.forEach(item -> {
                if (item.getStatus() != 4)
                    exhibitionList.remove(item);
            });
        }
        return exhibitionList;
    }

    @Override
    @NullDisable
    public List<Exhibition> queryExhibitionsByStatusAndKeyWord(String keyWord, Integer... status) {
        List<Exhibition> list = new ArrayList<>();
        List<Exhibition> keyList = exhibitionDao.queryExhibitionsByKeyWord(keyWord);
        keyList.forEach(item -> {
            for (int i : status) {
                if (i == item.getStatus())
                    list.add(item);
            }
        });
        return list;
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
        if (null == exhibition.getStatus() || userInformationDao.selectById(userId).getUserPermission() == 0)
            exhibition.setStatus(0);
        return exhibitionDao.modifyExhibition(exhibition);
    }

    @Override
    @NullDisable
    public int modifyExhibitionStatus(Integer id, Integer userId, Integer status) {
        if (userInformationDao.selectById(userId).getUserPermission() == 0)
            return -1;//权限不足
        return exhibitionDao.modifyExhibitionStatus(id, status);
    }

    @Override
    @NullDisable
    public int deleteExhibition(Integer id, Integer userId) {
        if (exhibitionDao.queryExhibitionByID(id).getStatus() != 0 && userInformationDao.selectById(userId).getUserPermission() == 0)
            return -1;//权限不足
        return exhibitionDao.deleteExhibition(id);
    }

    @Override
    @NullDisable
    public List<Exhibition> queryAllExhibitionsByUserId(Integer userId) {
        Integer userCompanyId = userInformationDao.selectById(userId).getUserCompanyId();
        System.out.println("userCompanyId: " + userCompanyId);
        if (null != userCompanyId) {
            List<Exhibition> list = new ArrayList<>();
            List<Integer> companyIdList = exhibitionDao.queryExhibitionByCompanyId(userCompanyId);
            if (null != companyIdList) {
                companyIdList.forEach(item -> {
                    Exhibition exhibition = exhibitionDao.queryExhibitionByID(item);
                    //4为删除状态
                    if (null != exhibition && exhibition.getStatus() != 4) {
                        list.add(exhibition);
                    }
                });
            }
            return list;
        } else {
            return null;
        }
    }
}
