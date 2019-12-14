package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

/**
 * 对商品的业务层描述
 */
public interface ItemService {

    /**
     * 查询所有的商品信息
     *
     * @return
     */
    EasyUITable findObject(Integer page, Integer rows);

    /**
     * 根据表单信息进行插入一个新的商品信息
     *
     * @param item
     * @return
     */
    void insertNewItem(Item item, ItemDesc itemDesc);

    /**
     * 更新商品数据
     *
     * @param item
     */
    public void updataItem(Item item,ItemDesc itemDesc);

    /**
     * 根据一个或者多个id更新状态码的值
     * @param ids
     * @param status
     */
    public void updateStatus(Long[] ids,Integer status);

    /**
     * 删除商品信息
     * @param ids
     */
    void deleteItem(Long[] ids);

    /**
     * 查询商品的详情信息
     * @param itemId
     * @return
     */
    ItemDesc finbItemDescById(Long itemId);

    /**
     * 根据id查询item。用于web
     * @param itemId
     * @return
     */
    Item findItemById(Long itemId);
}
