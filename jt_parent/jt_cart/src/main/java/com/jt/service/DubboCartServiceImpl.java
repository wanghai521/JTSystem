package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 购物车业务的实现类
 *
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@Service // 使用dubbo的注解
public class DubboCartServiceImpl implements DubboCartService {
    @Autowired
    private CartMapper cartMapper;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Cart> findCartListByUserId(Long userId) {

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        return cartList;
    }

    /**
     * 更细商品的数量信息
     * SQL:update tb_cart set num=#{num},updated=#{updated} where user_id=#{userId} and item_id=#{itemID}
     *
     * @param cart
     */
    @Override
    public void updateCartNum(Cart cart) {
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum())
                .setUpdated(new Date());

        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", cart.getUserId())
                .eq("item_id", cart.getItemId());
        cartMapper.update(cartTemp, updateWrapper);
    }

    /**
     * 购物车的商品删除
     * @param cart
     */
    @Override
    public void deleteCart(Cart cart) {

        cartMapper.delete(new QueryWrapper<Cart>(cart));

    }

    /**
     * 实现商品的加入购物车
     * 1、判断是否已存在该商品
     * 2、已经存在就是增加一个数量
     * 2、不存在就是数据库的插入
     * @param cart
     */
    @Override
    public void insertCart(Cart cart) {
        // 1、判断商品是否已经先存在|
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId())
                .eq("item_id", cart.getItemId());
        Cart cartDB= cartMapper.selectOne(queryWrapper);
        // 判断查询的结果是否为空，也就是是否为已经存在数据库
        if (cartDB == null){
            // 执行插入操作
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else {
            // 执行更细操作
            int num = cart.getNum() + cartDB.getNum();
            Cart cartTemp = new Cart();
            cartTemp.setNum(num)
                    .setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }
    }
}
