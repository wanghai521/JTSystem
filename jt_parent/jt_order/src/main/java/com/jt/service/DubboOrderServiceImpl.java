package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@Service // 使用dubbo的注解
public class DubboOrderServiceImpl implements DubboOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 实现订单的提交，也就是数据的插入
     * 需要在执行完之后，返回订单的编号
     * 需要事务控制
     *
     * @param order
     * @return
     */
    @Override
    @Transactional
    public String insertOrder(Order order) {

        Date date = new Date();
        // 订单号为：登录用户id+当前的时间戳|
        String orderId = order.getUserId() + System.currentTimeMillis() + "";

        // 1、入库order对象
        order.setOrderId(orderId)
                .setStatus(1)
                .setCreated(date)
                .setUpdated(date);
        // 执行插入
        orderMapper.insert(order);

        // 2、订单的物流入库
        OrderShipping shipping = order.getOrderShipping();
        shipping.setOrderId(orderId)
                .setCreated(date)
                .setUpdated(date);
        // 物流信息入库
        orderShippingMapper.insert(shipping);

        // 3、实现订单商品入库
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId)
                    .setCreated(date)
                    .setUpdated(date);
            orderItemMapper.insert(orderItem);
        }

        System.out.println("订单入库成功");
        return orderId;
    }

    /**
     * 跟具用户的ID信息进行数据库的订单查询
     *
     * @param id
     * @return
     */
    @Override
    public Order findOrderById(String id) {

        Order order = orderMapper.selectById(id);

        OrderShipping shipping = orderShippingMapper.selectById(id);

        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", id);
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);

        return order.setOrderShipping(shipping).setOrderItems(orderItems);
    }
}
