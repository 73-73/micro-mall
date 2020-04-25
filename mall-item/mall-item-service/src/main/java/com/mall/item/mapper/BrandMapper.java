package com.mall.item.mapper;

import com.mall.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 品牌表的通用Mapper接口
 *
 * @author pan
 * @create 2020-04-17-16:22
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 在关联表中新增记录
     *
     * @param cid
     * @param bid
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    void insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 通过bid删除与之关联的tb_category_brand表中的字段
     *
     * @param bid
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteCategoryBrand(@Param("bid") Long bid);

    /**
     * 通过分类id查询在此分类下的所有品牌
     *
     * @param cid
     * @return
     */
    @Select("SELECT * from tb_brand WHERE id IN(SELECT brand_id as id FROM tb_category_brand WHERE category_id = #{cid})")
    List<Brand> queryBrandsByCid(@Param("cid") Long cid);
}
