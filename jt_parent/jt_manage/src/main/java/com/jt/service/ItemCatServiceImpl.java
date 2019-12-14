package com.jt.service;

import com.jt.annotation.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther WangHai
 * @Date 2019/11/5
 * @Describle what
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;
//    @Autowired
//    private JedisCluster jedisCluster;

    /**
     * 根据商品的id查询类别的名称
     *
     * @param itemCatId
     * @return
     */
    @Override
    public String findItemCatName(Long itemCatId) {

        ItemCat itemCat = itemCatMapper.selectById(itemCatId);

        return itemCat.getName();
    }

    /**
     * 根据id查询树的节点，商品分类的树状
     *
     * @param id
     * @return
     */
    @Override
    @CacheFind(second = 50)
    public List<EasyUITree> findCatById(Long id) {
        // 根据parent_id查询数据
        List<ItemCat> itemCats = itemCatMapper.findItemCatByParebtId(id);

        // 进行数据封装
        List<EasyUITree> easyUITrees = new ArrayList<>();
        for (ItemCat itemCat : itemCats) {
            Long catId = itemCat.getId();
            String catName = itemCat.getName();
            String state = itemCat.getIsParent() ? "closed" : "open";

            // 将数据添加进list
            easyUITrees.add(new EasyUITree(catId, catName, state));

        }
        return easyUITrees;
    }

    /**
     * 实现缓存查询
     * 1、先查询缓存中是否存在该数据  key自己确定："ITEMCAT"+id
     * 2、如果缓存中没有这个数据，执行数据库查询，
     * 3、将查询数据库的数据进行转化json数据，并保存在redis缓存中
     * 4、如果缓存中有该数据，则不用查询数据库，直接从缓存中获取json数据
     * 5、将json数据转换成java对象
     *
     * @param id
     * @return
     */
/*    @Override
    public List<EasyUITree> findItemCatCache(Long id) {
        // 最终的返回值
        List<EasyUITree> easyUITrees = new ArrayList<>();

        String key = "ITEMCAT::" + id;
        // 从缓存中查询数据
        String cacheJson = jedisCluster.get(key);
        // 判断缓存中的数据是否为空
        if (StringUtils.isEmpty(cacheJson)) {
            // 执行数据库查询
            easyUITrees = findCatById(id);

            // 将数据转换成json字符串
            String json = ObjectMapperUtil.objectToJson(easyUITrees);
            // 将数据存进缓存，可以选择和之前的格式相同
            jedisCluster.set(key, json);
            System.out.println("执行第一次数据库查询");
        } else {
            // 执行到此，说明缓存中有该数据
            // 将json数据转换成Object
            easyUITrees = ObjectMapperUtil.jsonToObject(cacheJson, easyUITrees.getClass());
            System.out.println("执行了缓存查询");
        }

        return easyUITrees;
    }*/
}
