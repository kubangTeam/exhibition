package cn.edu.hqu.cst.kubang.exhibition.service.impl;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.Pagination;
import cn.edu.hqu.cst.kubang.exhibition.annotation.NullDisable;
import cn.edu.hqu.cst.kubang.exhibition.dao.AdvertisementDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Advertisement;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import cn.edu.hqu.cst.kubang.exhibition.service.IAdvertisementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  @author: sunquan
 *  @Date: 2020.03.22 10.42
 *  @Description: 广告的service层接口实现类
 */
@Service
public class AdvertisementServiceImpl implements IAdvertisementService {
    @Autowired
    private AdvertisementDao advertisementDao;

    @Value("${pagehelper.pageSize2}")
    private int pageSize2;//一页显示8个

    //列表合并去重
    @NullDisable
    public List<Advertisement> combineMultiList(List<Advertisement> list1, List<Advertisement> list2,
                                                List<Advertisement> list3) {
        List listAll = null;
        listAll = new ArrayList<Advertisement>();
        //合并列表
        listAll.addAll(list1);
        listAll.addAll(list2);
        listAll.addAll(list3);
        //去重
        listAll = new ArrayList<Advertisement>(new LinkedHashSet<>(listAll));
        return listAll;
    }

    //从Java中随机抽取List集合中特定个数的子项
    @NullDisable
    public List<Advertisement> getSubStringByRandom(List<Advertisement> list, int count) {
        List backList = null;
        backList = new ArrayList<Advertisement>();
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
    //旧版recommendAds
    /*
     //推荐广告页，每一页8个，暂时只推荐一个页面
    @Override
    @NullDisable
    public Map<String,Object>  recommendAds(int pageNum) {
        //1、筛选出通过审核的广告页 状态为2：审核通过
        //2、判断时间：开始时间早于当前时间，结束时间晚于当前时间；若过期则修改其状态为假删除
        //3、判断优先级：优先级从高到底为2 1 0 ，按照332来排列 前三个为高优先级，后三个为中优先级，最后两个为低优先级；
        //同种优先级随机选取。用户刷新一次，后台随机选取一页广告发送至前端。

        List<Advertisement> advertisementList = advertisementDao.selectByAdsStatus(2);
        List<Advertisement> priority2 = new ArrayList<>();
        List<Advertisement> priority1 = new ArrayList<>();
        List<Advertisement> priority0 = new ArrayList<>();

        //获取当前时间
        Date data = new Date();
        long value = data.getTime();
        data.setTime(value);

        //去掉不符合时间的广告横幅
        Iterator<Advertisement> it = advertisementList.iterator();
        while (it.hasNext()) {
            Advertisement advertisement = it.next();
            int compareStart = data.compareTo(advertisement.getStartTime());//前者小于后者返回-1；前者大于后者返回1；相等返回0
            int compareEnd = data.compareTo(advertisement.getEndTime());
            if (!(compareStart == 1 && compareEnd == -1)) {
                it.remove();
            }
        }
        //System.out.println(advertisementList);
        //从符合时间条件的广告中分出不同的优先级
        for (int i = 0; i < advertisementList.size(); i++) {
            if (advertisementList.get(i).getPriority() == 3) {
                priority2.add(advertisementList.get(i));
            } else if (advertisementList.get(i).getPriority() == 2) {
                priority1.add(advertisementList.get(i));
            } else if (advertisementList.get(i).getPriority() == 1) {
                priority0.add(advertisementList.get(i));
            } else {
                System.out.println("优先级字段出错");
            }
        }
        //从不同的优先级列表中随机选取n（3，3，2）个元素
        priority2 = getSubStringByRandom(priority2, 3);
        priority1 = getSubStringByRandom(priority1, 3);
        priority0 = getSubStringByRandom(priority0, 2);

        //合并list集合
        advertisementList = combineMultiList(priority2, priority1, priority0);
        Map<String,Object> map = new HashMap<>();
        map = Pagination.paginationAds(pageNum,pageSize2,advertisementList);
        return map;
    }
    * */
    //推荐广告页，每一页8个，暂时只推荐一个页面
    @Override
    @NullDisable
    public Map<String,Object>  recommendAds(int pageNum) {

        List<Advertisement> advertisementList = advertisementDao.selectByAdsStatus(2);
        List<Advertisement> result = new ArrayList<>();

        //获取当前时间
        Date data = new Date();
        long value = data.getTime();
        data.setTime(value);

        //如果发现有一个时间已经过期，就再找一个
        //首先找到优先级1--8的
        Iterator<Advertisement> it = advertisementList.iterator();
        while (it.hasNext()) {
            Advertisement advertisement = it.next();
            int priority = advertisement.getPriority();
            if (priority >= 1 && priority <= 8) {
                Date endTime = advertisement.getEndTime(); //结束时间
                int compareEnd = data.compareTo(endTime);
                if (compareEnd == 1) {
                    //如果过期了，再找一个
                    it.remove();
                    Advertisement ad =  findReplacement(it);
                    //修改ad的 优先级为advertisement的优先级
                    //修改优先级为advertisement的优先级的优先级为0
                    advertisementDao.updateAds(ad.getId(),ad.getStartTime(),ad.getEndTime(),ad.getPicture(),advertisement.getPriority());
                    advertisementDao.updateAds(advertisement.getId(),advertisement.getStartTime(),advertisement.getEndTime(),advertisement.getPicture(),0);
                    result.add(ad);
                }
                else{
                    result.add(advertisement);
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map = Pagination.paginationAds(pageNum,pageSize2,result);
        return map;
    }
    private Advertisement findReplacement(Iterator<Advertisement> it){
        Advertisement result = null;
        Iterator<Advertisement> it_temp = it;
        //获取当前时间
        Date data = new Date();
        long value = data.getTime();
        data.setTime(value);

        while (it.hasNext()) {
            Advertisement advertisement = it.next();
            int priority = advertisement.getPriority();

            int compareStart = data.compareTo(advertisement.getStartTime());//前者小于后者返回-1；前者大于后者返回1；相等返回0
            int compareEnd = data.compareTo(advertisement.getEndTime());

            if (priority == 0) { //找到优先级为0
                //日期不符合要求，跳过
                if (!(compareStart == 1 && compareEnd == -1)) {
                    continue;
                } else {//否则作为寻找最接近时间的目标
                    if (result != null) {//如果不为空，就要比较时间谁更加接近目前时间
                        //当前遍历的advertisement startTime比较早，就替换当前result
                        if(advertisement.getStartTime().compareTo(result.getStartTime())==-1){
                            result = advertisement;
                        }
                    } else {
                        result = advertisement;
                    }
                }
            }
        }
        it = it_temp;
        return result;
    }
    //检查发起的申请
    @Override
    public String examineAds(int id, int type) {
        //1、当用户前一个申请正在审核,即请求状态为1， 拒绝第二次申请。
        //2、设置用户最多同时存在的广告数量（已通过申请），例如最多两个
        int count = 0;
        if (type == 1) {//type为1代表商家；为0代表展会
            List<Advertisement> advertisementList = advertisementDao.selectByCompanyId(id);
            for (int i = 0; i < advertisementList.size(); i++) {
                if (advertisementList.get(i).getStatus() == 1) {
                    return "拒绝申请，上一个请求正在审核";
                }else if(advertisementList.get(i).getStatus() == 2){
                    count ++;
                }
                if(count==2){
                    return "审核通过的广告数以达到最大限额";
                }

            }
        }else if(type ==0){
            List<Advertisement> advertisementList = advertisementDao.selectByCompanyId(id);
            for (int i = 0; i < advertisementList.size(); i++) {
                if (advertisementList.get(i).getStatus() == 1) {
                    return "拒绝申请，上一个请求正在审核";
                }else if(advertisementList.get(i).getStatus() == 2){
                    count ++;
                }
                if(count==2){
                    return "审核通过的广告数以达到最大限额";
                }
            }
        }
        return "提交成功，等待生气审核";
    }

    @Override
    public Map<String, Object> updateAds(Advertisement advertisement) {
        Integer temp = null;
        String info = null;
        Map<String,Object> map = new HashMap<>();
        temp = advertisementDao.updateAds(advertisement.getId(),advertisement.getStartTime(),
                advertisement.getEndTime(),advertisement.getPicture(),advertisement.getPriority());
        //temp==1不一定修改成功了
        if(temp==1){
            info = "修改成功";
        }else{
            info = "修改失败";
        }
        map.put("info",info);
        return map;
    }
}
