package com.mall.item.mapper;

import com.mall.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 类目表的mapper接口
 * @author pan
 * @create 2020-02-02-14:39
 */
public interface CategoryMapper extends Mapper<Category> {

    /**
     * 通过品牌id查询所属的类目信息
     * @param pid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id in ( SELECT category_id id FROM tb_category_brand WHERE brand_id = #{pid} )")
    List<Category> queryCategoryByBid(@Param("pid") Long pid);

    /**
     * 根据cid删除关联表中相关的记录
     * @param cid
     */
    @Delete("DELETE FROM tb_category_brand WHERE category_id = #{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid") Long cid);
}
