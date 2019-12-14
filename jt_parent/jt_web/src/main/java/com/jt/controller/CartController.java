package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Reference(check = false) // 使用dubbo框架的注解
    private DubboCartService cartService;

    /**
     * 查看购物和的纪录，在页面中展示数据信息
     * 页面取值是"${cartList}"
     * @param model
     * @return
     */
    @RequestMapping("/show")
    public String show(Model model){

        // 1、获取当前登录用户的信息
        Long userId = UserThreadLocal.getUser().getId(); // 模拟的数据

        // 2、查询数据
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateNum(Cart cart){
        // 获取当前登录对象的信息
        Long userId = UserThreadLocal.getUser().getId(); // 模拟的
        cart.setUserId(userId);
        cartService.updateCartNum(cart);

        return SysResult.success();
    }

    /**
     * 删除购物车的商品
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){

        // 获取当前用户的信息
        Long userId = UserThreadLocal.getUser().getId();
        cart.setUserId(userId);

        cartService.deleteCart(cart);

        return "redirect:/cart/show"; // 重定向至购物车页面，实现刷新
    }

    /**
     * 实现商品的添加至购物车
     * 1、判断商品是否已经存在购物车
     * 2、已经存在购物车，就是直接进行商品数量的添加
     * 3、不存在的商品就件插入操作，默认的个数是1
     * @param cart
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String insertCart(Cart cart){
        // 获取当前用户的信息、
        Long userId = UserThreadLocal.getUser().getId();
        cart.setUserId(userId);
        cartService.insertCart(cart);

        return "redirect:/cart/show";
    }
}
