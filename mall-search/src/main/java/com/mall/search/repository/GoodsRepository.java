package com.mall.search.repository;

import com.mall.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**商品的Repository，用于提供相关的api
 * @author pan
 * @create 2020-04-26-12:05
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
