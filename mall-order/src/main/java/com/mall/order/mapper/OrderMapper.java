package com.mall.order.mapper;

import com.mall.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {

    @Select("SELECT * FROM `tb_order` o left JOIN `tb_order_detail` d on o.order_id = d.order_id LEFT JOIN `tb_order_status` s on o.order_id = s.order_id\n" +
            "WHERE o.user_id = #{userId} and s.`status` = #{status}")
    List<Order> queryOrderList(
            @Param("userId") Long userId,
            @Param("status") Integer status);

    @Select("<script>SELECT * FROM `tb_order` o left JOIN `tb_order_detail` d on o.order_id = d.order_id LEFT JOIN `tb_order_status` s on o.order_id = s.order_id" +
            " WHERE 1=1 <when test='key!=null'>and d.title like #{key} or o.buyer_nick like #{key}</when> <when test='status!=null'> and s.`status` = #{status}</when> </script>")
    List<Order> queryOrderPage(
            @Param("key") String key,
            @Param("status") Integer status);
}
