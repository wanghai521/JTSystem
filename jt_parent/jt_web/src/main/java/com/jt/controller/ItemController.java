package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther WangHai
 * @Date 2019/12/2
 * @Describle what
 */
@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 使用网络歇息，从后台数据管理进行数据的获取
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/{itemId}")
    public String toItem(@PathVariable Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item", item);
        ItemDesc itemDesc = itemService.finItemDescById(itemId);
        model.addAttribute("itemDesc", itemDesc);

        return "item";
    }
}
