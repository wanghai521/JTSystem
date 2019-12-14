package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

/**
 * 前台的商品业务层接口
 */
public interface ItemService {
    /**
     * 使用http网络协议，进行后台管理的数据获取
     * @param itemId
     * @return
     */
    Item findItemById(Long itemId);

    /**
     * 查询商品的详情信息
     * @param itemId
     * @return
     */
    ItemDesc finItemDescById(Long itemId);
}
