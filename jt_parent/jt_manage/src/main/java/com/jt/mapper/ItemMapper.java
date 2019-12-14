package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

import java.util.List;

/**
 * 商品信息表的持久层
 */
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 对上商品数据的分页查询
     * @param index
     * @param pagesize
     * @return
     */
    List<Item> findAllItems(Integer index,Integer pagesize);
}
