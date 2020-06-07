package com.mall.order.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.client.GoodClient;
import com.mall.common.IdWorker;
import com.mall.common.PageResult;
import com.mall.common.pojo.UserInfo;
import com.mall.order.interceptor.LoginInterceptor;
import com.mall.order.mapper.OrderDetailMapper;
import com.mall.order.mapper.OrderMapper;
import com.mall.order.mapper.OrderStatusMapper;
import com.mall.order.pojo.Order;
import com.mall.order.pojo.OrderDetail;
import com.mall.order.pojo.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper detailMapper;

    @Autowired
    private OrderStatusMapper statusMapper;

    @Autowired
    private GoodClient goodClient;



    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public Long createOrder(Order order) {
        // 生成orderId
        long orderId = idWorker.nextId();
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // 初始化数据
        order.setBuyerNick(user.getUsername());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(user.getId());
        this.orderMapper.insertSelective(order);

        // 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        // 初始状态为未付款
        orderStatus.setStatus(1);

        this.statusMapper.insertSelective(orderStatus);

        // 订单详情中添加orderId
        order.getOrderDetails().forEach(od -> od.setOrderId(orderId));
        // 保存订单详情,使用批量插入功能
        this.detailMapper.insertList(order.getOrderDetails());

        //更新库存
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for(OrderDetail orderDetail:orderDetails){
            Long skuId = orderDetail.getSkuId();
            Integer num = orderDetail.getNum();
            goodClient.updateStock(skuId,num);
        }
        logger.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());

        return orderId;
    }

    public Order queryById(Long id) {
        // 查询订单
        Order order = this.orderMapper.selectByPrimaryKey(id);

        // 查询订单详情
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(id);
        List<OrderDetail> details = this.detailMapper.select(detail);
        order.setOrderDetails(details);

        // 查询订单状态
        OrderStatus status = this.statusMapper.selectByPrimaryKey(order.getOrderId());
        order.setStatus(status.getStatus());
        return order;
    }

    public PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status) {
        try {
            // 分页
            PageHelper.startPage(page, rows);
            // 获取登录用户
            UserInfo user = LoginInterceptor.getLoginUser();
            // 创建查询条件
            Page<Order> pageInfo = (Page<Order>) this.orderMapper.queryOrderList(user.getId(), status);

            return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), pageInfo);
        } catch (Exception e) {
            logger.error("查询订单出错", e);
            return null;
        }
    }

    @Transactional
    public Boolean updateStatus(Long id, Integer status) {
        OrderStatus record = new OrderStatus();
        record.setOrderId(id);
        record.setStatus(status);
        // 根据状态判断要修改的时间
        switch (status) {
            case 2:
                // 付款
                record.setPaymentTime(new Date());
                break;
            case 3:
                // 发货
                record.setConsignTime(new Date());
                break;
            case 4:
                // 确认收获，订单结束
                record.setEndTime(new Date());
                break;
            case 5:
                // 交易失败，订单关闭
                record.setCloseTime(new Date());
                break;
            case 6:
                // 评价时间
                record.setCommentTime(new Date());
                break;
            default:
                return null;
        }
        int count = this.statusMapper.updateByPrimaryKeySelective(record);
        return count == 1;
    }

}
