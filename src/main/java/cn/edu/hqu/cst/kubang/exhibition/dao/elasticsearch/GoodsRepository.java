package cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch;

import cn.edu.hqu.cst.kubang.exhibition.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author SunChonggao
 * @Date 2020/4/13 15:14
 * @Version 1.0
 * @Description:
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods, Integer> {


}

