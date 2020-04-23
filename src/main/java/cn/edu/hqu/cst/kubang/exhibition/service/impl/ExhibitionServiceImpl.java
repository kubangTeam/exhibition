package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.dao.ExhibitionDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.entity.ExhibitionNew;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExhibitionServiceImpl implements IExhibitionService {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private Exhibition exhibition;

    @Autowired
    private UserInformationDao userInformationDao;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Value("${pagehelper.pageSize1}")
    private int pageSize1;//一页显示10个


    @Override
    public int holdExhibition(int Id, String name, Date startTime,
                              Date endTime, int exhibitionHallId,
                              int session, String period, String introduce, String picPath) {
        exhibition.setContractorId(Id);
        exhibition.setName(name);
        exhibition.setStartTime(startTime);
        exhibition.setEndTime(endTime);
        exhibition.setExhibitionHallId(exhibitionHallId);
        exhibition.setSession(session);
        exhibition.setPeriod(period);
        exhibition.setIntroduction(introduce);
        exhibition.setPicture(picPath);
        //设置认证状态 1 提交成功，待审核
        exhibition.setStatus(1);
        exhibition.setTel(userInformationDao.GetUserInfoFromId(Id).getUserAccount());
        if (exhibitionDao.saveExhibition(exhibition) == 1) {
            return 1;
        } else return 0;
        //设置联系方式
        //1、查询该展会承办方是否存在
        //数据库索引好像是从0开始，那么这个判断会出现错误
//       if(accountServiceImp.isOrganizerOrNot(Id)!=0){
//           int organizerId = accountServiceImp.isOrganizerOrNot(Id);
//           //查询账号表里面与该公司对于的账号的所有电话号码
//            List<UserInformation> list = userInformationDao.GetUserInfoFromOrganizerId(organizerId);
//            for(UserInformation temp:list){
//
//            }
//
//       }


    }

    /**
     * 返回即将上线的展会信息
     *
     * @return
     */
    @Override
    public List<Exhibition> queryReadyToStartExhibitionInfo() {
        //查询审核通过的展会列表
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByStatus(2);

        //获取当前时间
        Date data = new Date();
        long value = data.getTime();
        data.setTime(value);

        //去掉不符合时间的展会
        Iterator<Exhibition> it = exhibitionList.iterator();
        while (it.hasNext()) {
            exhibition = it.next();
            int compareStart = data.compareTo(exhibition.getStartTime());//前者小于后者返回-1；前者大于后者返回1；相等返回0
            int compareEnd = data.compareTo(exhibition.getEndTime());
            if (!(compareStart == 1 && compareEnd == -1)) {
                it.remove();
            }
        }
        System.out.println(exhibitionList);
        return exhibitionList;
    }

    /**
     * 根据展会id返回展会具体信息
     */
    @Override
    public ExhibitionNew queryExhibitionDetailById(int exhibitionId) {
        ExhibitionNew exhibitionNew = exhibitionDao.queryExhibitionDetailsByID(exhibitionId);
        List<String> exhibitionPic = exhibitionDao.queryExbitionPicById(exhibitionId);
        exhibitionNew.setPicture(exhibitionPic);
//        System.out.println(exhibitionNew.toString());
        return exhibitionNew;
    }


//    //查询所有展会 不包括删除
//    @Override
//    public List<Exhibition> queryAll() {
//        List<Exhibition> exhibitionList = exhibitionDao.queryAllExhibitions();
//        return exhibitionList;
//    }
//    @Override
//    @NullDisable
//    public PageInfo<Exhibition> queryAllExhibitions(int pageNum) {
//        PageHelper.startPage(pageNum, pageSize1);
//        List<Exhibition> exhibitionList = exhibitionDao.queryAllExhibitions();
//        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
//        return pageInfo;
//    }
//
//
//    //根据 状态查询所有 不包括删除
//    @Override
//    @NullDisable
//    public PageInfo<Exhibition> queryExhibitionsByStatus(Integer status, int pageNum) {
//        PageHelper.startPage(pageNum, pageSize1);
//        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByStatus(status);
//        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
//        return pageInfo;
//    }
//
//    //根据 关键词查询所有  不包括删除
//    @Override
//    @NullDisable
//    public PageInfo<Exhibition> queryExhibitionsByKeyWord(String keyWord, int pageNum) {
//        PageHelper.startPage(pageNum, pageSize1);
//        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByKeyWord(keyWord.split(" "));
//        PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
//        return pageInfo;
//    }
//
//    @Override
//    public PageInfo<Exhibition> queryAllExhibitionsByUserId(Integer userId, int pageNum) {
//        return null;
//    }
//
//    //根据状态和关键词查询 比如查找未通过审核的关键词为“1”的所有展品 不包括已删除
//    @Override
//    @NullDisable
//    public PageInfo<Exhibition> queryExhibitionsByStatusAndKeyWord(String keyWord, int pageNum, Integer... status) {
//        PageHelper.startPage(pageNum, pageSize1);
//        List<Exhibition> keyList = exhibitionDao.queryExhibitionsByKeyWord(keyWord.split(" "));
//        PageInfo<Exhibition> pageInfo = new PageInfo<>(keyList);
//        return pageInfo;
//    }
//
//    @Override
//    public int saveExhibition(Exhibition exhibition) {
//        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
//                && StringUtils.isEmpty(exhibition.getExhibitionHallId())) {
//            System.out.println("name start_time end_time exhibiton_hall_id 不允许为空");
//            return -1;
//        }
//        exhibition.setStatus(0);
//        int i = exhibitionDao.saveExhibition(exhibition);
//        return i;
//    }
//
//    @Override
//    public int modifyExhibition(Exhibition exhibition, Integer userId) {
//        //在数据库中 name start_time end_time exhibiton_hall_id status为非空字段
//        if (StringUtils.isEmpty(exhibition.getStartTime()) && StringUtils.isEmpty(exhibition.getEndTime())
//                && StringUtils.isEmpty(exhibition.getExhibitionHallId()) && null == userId) {
//            System.out.println("name start_time end_time exhibiton_hall_id userId不允许为空");
//            return -1;
//        }
//        /**前端根据用户的性质，   如果是普通用户，那么只有在0状态才可以修改，修改界面不显示status，后台得到的结果是null
//         如果是管理员，那么可以修改任何状态，修改界面显示status且必须填写，后台得到的结果不是null
//         这样根据是不是null来判断是不是管理员，为了确保正确性，可以进行双重判断
//         */
//        if (null == exhibition.getStatus() || userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
//            exhibition.setStatus(0);
//        return exhibitionDao.modifyExhibition(exhibition);
//    }
//
//    @Override
//    @NullDisable
//    public int modifyExhibitionStatus(Integer id, Integer userId, Integer status) {
//        if (userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
//            return -1;//权限不足
//        return exhibitionDao.modifyExhibitionStatus(id, status);
//    }
//
//    @Override
//    public int deleteExhibition(Integer id, Integer userId) {
//        return 0;
//    }

    /**
     @Override
     @NullDisable public int deleteExhibition(Integer id, Integer userId) {
     if (exhibitionDao.queryExhibitionByID(id).getStatus() != 0 && userInformationDao.GetUserInfoFromId(userId).getUserPermission() == 0)
     return -1;//权限不足
     return exhibitionDao.deleteExhibition(id);
     }

     //根据用户id查询他的公司的展品
     @Override
     @NullDisable public PageInfo<Exhibition> queryAllExhibitionsByUserId(Integer userId, int pageNum) {
     PageHelper.startPage(pageNum, pageSize1);
     List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByUserId(userId);
     PageInfo<Exhibition> pageInfo = new PageInfo<>(exhibitionList);
     return pageInfo;
     }
     **/

}
