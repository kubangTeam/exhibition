package cn.edu.hqu.cst.kubang.exhibition.dao.elasticsearch;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import cn.edu.hqu.cst.kubang.exhibition.entity.Exhibition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author SunChonggao
 * @Date 2020/4/28 22:22
 * @Version 1.0
 * @Description:
 */
@Repository
public interface CompanyRepository extends ElasticsearchRepository<Company, Integer> {

}
