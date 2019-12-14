package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;

import java.util.List;

/**
 * 商品类型表的持久层映射
 */
public interface ItemCatMapper extends BaseMapper<ItemCat>{

    /**
     * 根据parent_id查询itemCat
     * @param id
     * @return
     */
    List<ItemCat> findItemCatByParebtId(Long id);
}
