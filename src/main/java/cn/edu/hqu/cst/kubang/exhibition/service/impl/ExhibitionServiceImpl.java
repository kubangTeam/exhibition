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
import com.google.common.util.concurrent.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@Service
public class ExhibitionServiceImpl implements IExhibitionService{

    @Autowired
    private ExhibitionDao exhibitionDao;

    @Autowired
    private Exhibition exhibition;

    @Autowired
    private OrganizerInformationDao organizerInformationDao;

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

    @Resource(name = "redisKeyDb")
    private RedisTemplate<String, Object> redisKeyDb;

    ListeningExecutorService executorService = MoreExecutors.
            listeningDecorator(Executors.newFixedThreadPool(1));


//    public Map<String,Object> addExhibitionCity(Exhibition exhibition){
//        String city = exhibitionHallSevice.findCityNameByCityCode(exhibition.getExhibitionHallId());
//        //String Exhibition = null;
//        //String CityName = null;
//        Map<String ,Object>map = new HashMap<>();
//        map.put("Exhibition",exhibition);
//        map.put("CityName",city);
//        return map;
//    }
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
    //管理员查询所有展会
    //By Timor 2020/5/18
    public List<Exhibition> queryAllExhibitionInfoByAdmin()
    {
        return exhibitionDao.queryAllExhibitions();
    }

    @Override
    //举办方查询所有展会
    //By Timor 2020/5/18
    public List<Exhibition> queryAllExhibitionInfoByOrganizerID(int organizerID)
    {
        return exhibitionDao.getExhibitionsByOrganizerID(organizerID);
    }

    @Override
    //用户查询他参加过的展会
    //By Timor 2020/5/18
    public List<Exhibition> queryExhibitionInfoByUser(int userID)
    {
        //暂未实现
        return null;
    }

    @Override
    //根据账号id和展会状态查询展会信息 若为管理员则对所有展会按照状态查询 若为承办方 则按照承办方的所举办的展会的状态来查询
    //By Timor 2020/5/18
    public List<Exhibition> queryExhibitionInfoByUserIdAndStatus(int userId, int status) {
        ArrayList<Exhibition> result= new ArrayList<>();
        //如果是举办方(organizer)
        if(accountServiceImp.identifyUser(userId)!="承办方"){
            List<Exhibition> list = exhibitionDao.getExhibitionsByOrganizerID(userId);
            for(Exhibition exhibition:list){
                if(exhibition.getStatus() == status){
                    result.add(exhibition);
                }
            }
        }
        else //有管理员 ID？？
        {

        }
        return result;
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



    @Override
    public List<Goods>  queryRandomGoodsByExhibitionId(int exhibitionId){
        List<Integer> goodsIdList =new ArrayList<>();
        List<Goods> result = new ArrayList<>();
        List<GoodsJoinExhibition> goodsJoinExhibitions = null;
        if(goodsJoinExhibitionDao.selectByExhibitionId(exhibitionId)!=null){
            goodsJoinExhibitions = goodsJoinExhibitionDao.selectByExhibitionId(exhibitionId);
            for(GoodsJoinExhibition goodsJoinExhibition:goodsJoinExhibitions){
                if(goodsDao.selectGoodsById(goodsJoinExhibition.getGoodsId())!=null){
                    goodsIdList.add(goodsJoinExhibition.getGoodsId());
                }
                else
                    continue;
            }
            List<Integer> goodsIdListAfterRandom = getRandomNumList(Constants.COUNT_RECOMMEND_2,goodsIdList);
            for(Integer goodsId : goodsIdListAfterRandom) {
                Goods goods = goodsDao.selectGoodsById(goodsId);
                if(goodsDao.selectGoodsPicByGoodsId(goodsId).get(0)!=null) {
                    GoodsPic goodsPic = goodsDao.selectGoodsPicByGoodsId(goodsId).get(0);
                    String image = goodsPic.getPic();
                    goods.setImage(image);
                }
                result.add(goods);
            }
        }
        return result;
    }
    private <T> List<T> getRandomNumList(int nums, List<T> list) {
        List<T> result = new ArrayList<>();
        List temp = new ArrayList<>();
        Random r = new Random();
        while(result.size() < nums){
            int num = r.nextInt(list.size());
            if(!temp.contains(num)) {
                result.add(list.get(num));
                temp.add(num);
            }
        }
        return result;
    }
    @Override
    public List<Exhibition> queryOnGoing() {
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

        }
        return exhibitionList;
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
    @Override
    public List getExhibitionIdInRedis(){
        Set sets = redisKeyDb.opsForZSet().range("Exhibition",0, -1);
        return new ArrayList<>(sets);
    }
    @Override
    public void addExhibitionIntoRedis(int id){
        redisKeyDb.opsForZSet().add("Exhibition",id, 0);
    }
    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public boolean updateExhibitionInRedis()throws Exception{
        ListenableFuture<Boolean> future = executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<Exhibition> list = queryOnGoing();
                for(Exhibition exhibition : list){
                    redisKeyDb.opsForZSet().add("Exhibition", exhibition.getId(), 0);
                    System.out.println(exhibition.getId());
                }
                return true;
            }
        });
        return true;
    }


}
