package com.mall.search.repository;

import com.mall.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author pan
 * @create 2020-04-26-12:05
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
