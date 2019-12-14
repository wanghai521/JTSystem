package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据商品id查询类别的name
     *
     * @param itemCatId
     * @return
     */
    @RequestMapping("queryItemName")
    public String findItemCatName(Long itemCatId) {

        return itemCatService.findItemCatName(itemCatId);
    }

    /**
     * 通过parentId查询目录的结构
     *
     * @return
     */
    @RequestMapping("list")
    public List<EasyUITree> findCatById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        // 使用了换aop后  2019.12.1
        return itemCatService.findCatById(id);
        // 先查询缓存，再查询数据库
//        return itemCatService.findItemCatCache(id);
    }
}
