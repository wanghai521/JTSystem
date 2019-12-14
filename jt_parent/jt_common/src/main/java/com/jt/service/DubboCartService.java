package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

/**
 * 购物车的业务层接口
 */
public interface DubboCartService {

    /**
     * 根据用户信息查询购物车信息
     * @param userId
     * @return
     */
    List<Cart> findCartListByUserId(Long userId);

    /**
     * 更新购物车的商品数量信息
     * @param cart
     */
    void updateCartNum(Cart cart);

    /**
     * 实现购物车商品的删除
     * @param cart
     */
    void deleteCart(Cart cart);

    /**
     * 实现商品的加入购物车
     * @param cart
     */
    void insertCart(Cart cart);
}
