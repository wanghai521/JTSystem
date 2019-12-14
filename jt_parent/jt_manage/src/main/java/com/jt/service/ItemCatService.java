package com.jt.service;

import com.jt.vo.EasyUITree;

import java.util.List;

/**
 * 商品类目的业务层规范
 */
public interface ItemCatService {

    /**
     * 实现根据商品id查询类别名称
     *
     * @param itemCatId
     * @return
     */
    String findItemCatName(Long itemCatId);

    /**
     * 根据id查询目录机构树状的节点名称
     * @param id
     * @return
     */
    List<EasyUITree> findCatById(Long id);

    /**
     *先查询缓存中是否有数据，无数据再去查询数据库
     * @param id
     * @return
     */
//    List<EasyUITree> findItemCatCache(Long id);
}
