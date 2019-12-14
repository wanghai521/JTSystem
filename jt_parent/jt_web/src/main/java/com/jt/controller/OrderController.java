package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 订单处理的控制层
 *
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@RequestMapping("/order")
@Controller
public class OrderController {

    @Reference(check = false) //使用dubbo的注解
    private DubboCartService cartService;
    @Reference(check = false)
    private DubboOrderService orderService;

    /**
     * 订单确认页，需要获取购物车的信息
     * 通过userId
     * 页面获取数据："${carts}"
     *
     * @param model
     * @return
     */
    @RequestMapping("/create")
    public String create(Model model) {

        // 获取当前的用户信息
        Long userId = UserThreadLocal.getUser().getId();
        // 查询购物车的商品信息
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        // 存入域中
        model.addAttribute("carts", cartList);

        return "order-cart"; //执行视图解析器方法
    }

    /**
     * 实现订单的提交
     *
     * @param order
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult insertOrder(Order order) {
        Long userId = UserThreadLocal.getUser().getId();
        order.setUserId(userId);

        String orderId = orderService.insertOrder(order);
        if (StringUtils.isEmpty(orderId)) {
            // 如果返回值为空
            return SysResult.fail();
        }

        return SysResult.success(orderId);
    }

    /**
     * 根据orderId查询数据库纪录
     * 页面取值 ： "${order.orderId}"
     *
     * @return
     */
    @RequestMapping("/success")
    public String findOrderById(String id, Model model) {

        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);

        return "success";
    }

}
