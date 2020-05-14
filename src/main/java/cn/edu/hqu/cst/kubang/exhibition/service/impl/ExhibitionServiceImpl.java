package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.ComparatorImpl;
import cn.edu.hqu.cst.kubang.exhibition.Utilities.Constants;
import cn.edu.hqu.cst.kubang.exhibition.Utilities.Pagination;
import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.*;
import cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch.ExhibitionRepository;
import cn.edu.hqu.cst.kubang.exhibition.entity.*;
import cn.edu.hqu.cst.kubang.exhibition.service.IExhibitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExhibitionServiceImpl implements IExhibitionService, Constants {

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private Exhibition exhibition;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private CompanyJoinExhibitionDao companyJoinExhibitionDao;

    @Autowired
    private ExhibitionHallSeviceImpl exhibitionHallSevice;

    @Value("${pagehelper.pageSize1}")
    private int pageSize1;//一页显示10个

    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个

    @Value("${pagehelper.pageSize3}")
    private int pageSize3;//一页显示4个

    @Autowired
    private GoodsJoinExhibitionDao goodsJoinExhibitionDao;

    @Autowired
    private  GoodsJoinExhibition goodsJoinExhibition;

    @Autowired
    private ExhibitionSubarea exhibitionSubarea;

    @Autowired
    private ExhibitionSubareaDao exhibitionSubareaDao;


//    public Map<String,Object> addExhibitionCity(Exhibition exhibition){
//        String city = exhibitionHallSevice.findCityNameByCityCode(exhibition.getExhibitionHallId());
//        //String Exhibition = null;
//        //String CityName = null;
//        Map<String ,Object>map = new HashMap<>();
//        map.put("Exhibition",exhibition);
//        map.put("CityName",city);
//        return map;
//    }

    @Override
    public int holdExhibition(int Id, String name, Date startTime,
                              Date endTime, int exhibitionHallId,
                              int session, String period, String introduce, String picPath) {
        //设置承办方id为userId
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
        exhibition.setTel(userDao.GetUserInfoFromId(Id).getUserAccount());
        if (exhibitionDao.saveExhibition(exhibition) == 1) {
            exhibitionRepository.save(exhibition);//将该展会添加到ES中
            return exhibition.getId();

        }else return 0;
    }

    @Override
    public int addSubareaInfo(List<String> subAreaList, int exhibitionId) {
        String tag =null;
        exhibitionSubarea.setExhibitionId(exhibitionId);
        for (String temp:subAreaList){
            exhibitionSubarea.setSubarea(temp);
            int  i= exhibitionSubareaDao.insertExhibitionSubareaInfo(exhibitionSubarea);
            if(i==1){
                tag = temp;
                continue;
            }

            else break;
        }
        if(tag!=subAreaList.get(subAreaList.size()-1)){
            return 0;
        }else return 1;
    }

    @Override
    public List<Exhibition> queryAllExhibitionInfo() {
        return null;
    }



    @Override
    public List<Exhibition> queryExhibitionInfoByUserId(int userId) {
        if(accountServiceImp.identifyUser(userId) == "商家"){

        }
        return null;
    }

    @Override
    public List<Exhibition> queryExhibitionInfoByUserIdAndStatus(int userId, int status) {
        return null;
    }



    @Override
    public Map<String,Object> queryReadyToStartExhibitionInfo(int pageNum) {
        Map<String,Object> map = new HashMap<>();
        List<Exhibition> readyToStartExhibition =null;
        String info = null;
        //查询审核通过的展会列表
        List<Exhibition> exhibitionList = exhibitionDao.queryExhibitionsByStatus(5);
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
            if (!(compareStart == -1 && compareEnd == -1)) {//起始时间大于当前时间，且结束时间大于当前时间
                it.remove();
            }
        }
        map = Pagination.pagination(pageNum,pageSize2,exhibitionList);
        return map;
    }

    /**
     * 根据展会id返回展会具体信息
     */
    @Override
    public ExhibitionNew queryExhibitionDetailById(int exhibitionId) {
        ExhibitionNew exhibitionNew = exhibitionDao.queryExhibitionDetailsByID(exhibitionId);
        return exhibitionNew;
    }

    @Override
    public List<Exhibition> queryAll(){
        return exhibitionDao.queryAllExhibitions();
    }



    //从Java中随机抽取List集合中特定个数的子项
    @NullDisable
    public List<Goods> getSubStringByRandom(List<Goods> list, int count) {
        List backList = null;
        backList = new ArrayList<Goods>();
        Random random = new Random();
        int backSum = 0;
        if (list.size() >= count) {
            backSum = count;
        } else {
            backSum = list.size();
        }
        for (int i = 0; i < backSum; i++) {
            int target = random.nextInt(list.size());
            backList.add(list.get(target));
            list.remove(target);
        }
        return backList;
    }
    //从Java中随机抽取List集合中特定个数的子项
    @NullDisable
    public List<Exhibition> getFirstFourSubString(List<Exhibition> list, int count) {
        List backList = null;
        backList = new ArrayList<Exhibition>();
        Random random = new Random();
        int backSum = 0;
        if (list.size() >= count) {
            backSum = count;
        } else {
            backSum = list.size();
        }
        backList = list.subList(0,backSum);
        return backList;
    }
    @Override
    public Map<String,Object>  queryAllGoodsByExhibitionId(int exhibitionId){
        List<Goods> goodsList =new ArrayList<Goods>();
        List<GoodsJoinExhibition>goodsJoinExhibitions = null;
        Map<String,Object> map = new HashMap<>();
        String info = null;
        if(goodsJoinExhibitionDao.selectByExhibitionId(exhibitionId)!=null){
            goodsJoinExhibitions = goodsJoinExhibitionDao.selectByExhibitionId(exhibitionId);
            for(GoodsJoinExhibition goodsJoinExhibition:goodsJoinExhibitions){
                if(goodsDao.selectGoodsById(goodsJoinExhibition.getGoodsId())!=null){
                    Goods goods = goodsDao.selectGoodsById(goodsJoinExhibition.getGoodsId());
                    goodsList.add(goods);
                }else{
                    continue;
                }
            }
            goodsList = insertImageIntoGoods(goodsList);
            goodsList = getSubStringByRandom(goodsList,pageSize3);
            map.put("goodsList",goodsList);
        }else{
            info = "无展品参展";
            map.put("info",info);
        }
        return map;
    }



    @Override
    public Map<String,Object> queryOngoingExhibitionInfo() {
        Map<String,Object> map = new HashMap<>();
        //查询审核通过的展会列表 初审通过为2 终审通过为5
        List<Exhibition> exhibitionList = null;
        if(exhibitionDao.queryExhibitionsByStatus(5)!=null){
            exhibitionList = exhibitionDao.queryExhibitionsByStatus(5);
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
                if (!(compareStart == 1 && compareEnd == -1)) {//起始时间小于当前时间且结束时间大于当前时间
                    it.remove();
                }
            }
            Comparator comp = new ComparatorImpl();
            Collections.sort(exhibitionList,comp);
            exhibitionList = getFirstFourSubString(exhibitionList,4);
            map.put("info","查询成功");
            map.put("exhibitionList",exhibitionList);
        }else{
            map.put("info","展会不存在或者认证状态出错");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryGoodsByExhibitionIdAndSubareaId(int exhibitionId, int subAreaId, int pageNum) {
        Map<String,Object> map = new HashMap<>();
        List<GoodsJoinExhibition>goodsJoinExhibitions = null;
        String info =null;
        List<Goods> goodsList =new ArrayList<Goods>();
        if(goodsJoinExhibitionDao.selectByExhibitionIdAndSubareaId(exhibitionId,subAreaId)!=null){
            goodsJoinExhibitions =  goodsJoinExhibitionDao.selectByExhibitionIdAndSubareaId(exhibitionId,subAreaId);
            for(GoodsJoinExhibition goodsJoinExhibition:goodsJoinExhibitions){
                if(goodsDao.selectGoodsById(goodsJoinExhibition.getGoodsId())!=null){
                    Goods goods = goodsDao.selectGoodsById(goodsJoinExhibition.getGoodsId());
                    goodsList.add(goods);
                }else{
                    continue;
                }
            }
            goodsList = insertImageIntoGoods(goodsList);
            map = Pagination.paginationGoods(pageNum,pageSize2,goodsList);
        }else{
            info ="该分区无商品";
            map.put("info",info);
        }
        return map;
    }

    @Override
    public Map<String, Object> querySubareaByExhibitionId(int exhibitionId) {
        Map<String, Object> map = new HashMap<>();
        String info = null;
        List<ExhibitionSubarea> subInformation =null;
        Exhibition exhibition =null;
        subInformation = exhibitionSubareaDao.selectByExhibitionId(exhibitionId);
        exhibition = exhibitionDao.queryExhibitionByID(exhibitionId);
        if(subInformation!=null){
            if(exhibition.getStatus()==5){//展会状态为5，最终审核通过
                info = "查询成功";
                map.put("subInformation",subInformation);
            }else {
                info = "展会审核信息不对";
            }
        }else{
            info ="该商家分区信息不存在";
        }
        map.put("info",info);
        return map;
    }

    private List<Goods> insertImageIntoGoods(List<Goods> list){
        for(Goods goods : list) {
            if(goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId()).get(0)!=null){
                GoodsPic goodsPic = goodsDao.selectGoodsPicByGoodsId(goods.getGoodsId()).get(0);
                String image = goodsPic.getPic();
                goods.setImage(image);
            }else{
                //当商品没有图片时，删除该商品
                list.remove(goods);
            }
        }
        return list;
    }


}
