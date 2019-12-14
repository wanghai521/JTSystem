package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Auther WangHai
 * @Date 2019/11/5
 * @Describle what
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     * 分页查询所有的商品信息
     *
     * @return
     */
    @Override
    public EasyUITable findObject(Integer page, Integer rows) {

        // 计算起始查询的行数
        Integer index = (page - 1) * rows;
        List<Item> items = itemMapper.findAllItems(index, rows);
        EasyUITable easyUITable = new EasyUITable();
        easyUITable.setRows(items).setTotal(itemMapper.selectCount(null));

        return easyUITable;
    }

    /**
     * 插入新的商品信息
     * 2019.11.29加入商品详情信息的插入
     *
     * @param item
     * @return
     */
    @Override
    @Transactional // 添加事务控制
    public void insertNewItem(Item item, ItemDesc itemDesc) {
        Date date = new Date();
        // 设置其商品状态和更插入时间和更新时间
        item.setStatus(1).setCreated(date).setUpdated(date);
        // 插入数据
        itemMapper.insert(item);
        itemDesc.setItemId(item.getId()).setCreated(date).setUpdated(date);
        itemDescMapper.insert(itemDesc);

    }

    /**
     * 更新商品数据
     *
     * @param item
     */
    @Override
    @Transactional
    public void updataItem(Item item,ItemDesc itemDesc) {
        // 设置更新时间
        item.setUpdated(new Date());
        itemMapper.updateById(item);

        itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
        itemDescMapper.updateById(itemDesc);
    }

    /**
     * 更新状态码
     *
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, Integer status) {

        Item item = new Item();
        item.setStatus(status).setUpdated(new Date()); // 设置更新时间
        UpdateWrapper<Item> wrapper = new UpdateWrapper<>();
        // 将数组转为list列表
        List idList = Arrays.asList(ids);
        wrapper.in("id", idList);
        itemMapper.update(item, wrapper);
    }

    /**
     * 根据id删除商品信息
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteItem(Long[] ids) {

        /*UpdateWrapper<Item> deleteWrapper = new UpdateWrapper<>();
        List idList = Arrays.asList(ids);
        deleteWrapper.in("id", idList);
        itemMapper.delete(deleteWrapper);*/

        List idList = Arrays.asList(ids);
        itemMapper.deleteBatchIds(idList);
        itemDescMapper.deleteBatchIds(idList);
    }

    /**
     * 查询商品的详细信息
     *
     * @param itemId
     * @return
     */
    @Override
    public ItemDesc finbItemDescById(Long itemId) {
        // itemId在pojo中被标识为该表的id，所以这里直接用selectById
        ItemDesc itemDesc = itemDescMapper.selectById(itemId);
        return itemDesc;
    }

    /**
     * 实现web的数据获取，
     * @param itemId
     * @return
     */
    @Override
    public Item findItemById(Long itemId) {

        return itemMapper.selectById(itemId);
    }

}
