package cn.edu.hqu.cst.kubang.exhibition.service;

import cn.edu.hqu.cst.kubang.exhibition.dao.SearchDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.Search;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @Author SunChonggao
 * @Date 2020/5/2 10:37
 * @Version 1.0
 * @Description:搜索热词业务--Redis
 */
@Service
@Slf4j
@EnableScheduling
public class SearchService {

    ListeningExecutorService executorService = MoreExecutors.
                                                listeningDecorator(Executors.newFixedThreadPool(1));

    @Resource(name = "redisKeyDb")
    private RedisTemplate<String, Object> redisKeyDb;


    private SearchDao searchDao;

    @Autowired
    public SearchService(SearchDao searchDao){
        this.searchDao = searchDao;
    }


    public void addHotWord(String hotWord) throws Exception {
        if (StringUtils.isEmpty(hotWord))
            return;
        Long now = System.currentTimeMillis(); //记录热词的日期
        redisKeyDb.opsForZSet().incrementScore("hotWord", hotWord, 1); // 加入排序set
        redisKeyDb.opsForValue().set(hotWord, now); // 记录时间
    }

    public Set<Object> getHotWord(int topN) throws Exception {
        Set<Object> sets = redisKeyDb.opsForZSet()
                .reverseRangeByScore("hotWord", 0, Integer.MAX_VALUE, 0, topN);
        return sets;
    }

    //每天清除一次数据
    @Scheduled(cron = "0 0 1 * * ?")
    public Boolean clearHotWordOutTime() throws Exception {
        ListenableFuture<Boolean> future = executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Long now = System.currentTimeMillis();
                ValueOperations<String, Object> wordTime = redisKeyDb.opsForValue();
                Set<Object> sets = redisKeyDb.opsForZSet().reverseRange("hotWord", 0, Integer.MAX_VALUE);
                for (Object set : sets) {
                    String word = String.valueOf(set);
                    Long time = Long.valueOf(String.valueOf(wordTime.get(word)));
                    if ((now - time) > 2592000000L) { // 找到1个月未操作的数据
                        redisKeyDb.opsForZSet().remove("hotWord", word);
                        redisKeyDb.opsForValue().getOperations().delete(word);
                    }
                }
                return true;
            }
        });
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean v) {
                log.info("@HotWord: clear redis data for hot word month ago successfully");
            }
            @Override
            public void onFailure(Throwable throwable) {
                log.info("@HotWord: fail to clear redis data for hot word, message is {}", throwable.getMessage());
            }
        });
        return true;
    }
    public int saveSearchRecord(int userId, String keyword, int classification){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Search search = new Search();
        search.setUserId(userId);
        search.setSearchEntry(keyword);
        search.setSearchTime(timestamp);
        search.setClassification(classification);
        return searchDao.insertSearchRecord(search);
    }
}
