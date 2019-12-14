package com.jt.controller.web;

import com.jt.annotation.CacheFind;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 给前端页面做数据获取
 *
 * @Auther WangHai
 * @Date 2019/12/2
 * @Describle what
 */
@RestController // 返回的数据都是json串
@RequestMapping("/web/item")
public class WebItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据id进行数据查询
     *
     * @return
     */
    @RequestMapping("/findItemById")
    @CacheFind(second = 500)
    public Item findItemById(Long itemId) {

        return itemService.findItemById(itemId);
    }

    /**
     * 查询商品的详情信息
     * @param itemId
     * @return
     */
    @RequestMapping("/findItemDescById")
    @CacheFind(second = 500)
    public ItemDesc findItemDescById(Long itemId) {

        return itemService.finbItemDescById(itemId);
    }


}
