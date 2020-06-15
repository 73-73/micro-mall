package com.mall.order.controller;

import com.mall.client.GoodClient;
import com.mall.common.PageResult;
import com.mall.order.pojo.Order;
import com.mall.order.pojo.OrderDetail;
import com.mall.order.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("订单服务接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodClient goodClient;

    /**
     * 创建订单
     *
     * @param order 订单对象
     * @return 订单编号
     */
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid Order order) {
        // 获取这个订单里面的商品的库存
        for(OrderDetail orderDetail : order.getOrderDetails()){
            Long skuId = orderDetail.getSkuId();
            if(goodClient.querySkuById(skuId).getStock()<orderDetail.getNum()){
                //该sku库存不足
                return new ResponseEntity<>(orderDetail.getTitle(), HttpStatus.BAD_REQUEST);
            }
        }
        Long id = this.orderService.createOrder(order);
        return new ResponseEntity<>(id.toString(), HttpStatus.CREATED);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Order> queryOrderById(@PathVariable("id") Long id) {
        Order order = this.orderService.queryById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(order);
    }

    /**
     * 分页查询当前用户订单
     *
     * @param status 订单状态
     * @return 分页订单数据
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Order>> queryUserOrderList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "status", required = false) Integer status) {
        PageResult<Order> result = this.orderService.queryUserOrderList(page, rows, status);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据分页条件订单搜索详情页面
     * @param key
     * @param page
     * @param rows
     * @param status
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Order>> queryUserOrderPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "status", required = false) Integer status) {
        PageResult<Order> result = this.orderService.queryUserOrderPage(page, rows, status,key);
        if (result.getItems() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }



    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("{orderId}/{status}")
    public ResponseEntity<Boolean> updateStatus(@PathVariable("orderId") Long id, @PathVariable("status") Integer status) {
        Boolean boo = this.orderService.updateStatus(id, status);
        if (boo == null) {
            // 返回400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 返回204
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
