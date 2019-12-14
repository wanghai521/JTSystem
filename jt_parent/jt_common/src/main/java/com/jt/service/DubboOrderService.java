package com.jt.service;

import com.jt.pojo.Order;

/**
 * 订单管理的业务层接口
 */
public interface DubboOrderService {

    /**
     * 实现订单的提交，就是数据的插入
     * @param order
     * @return
     */
    String insertOrder(Order order);

    /**
     * 根据ID查询订单信息
     * @param id
     * @return
     */
    Order findOrderById(String id);
}
