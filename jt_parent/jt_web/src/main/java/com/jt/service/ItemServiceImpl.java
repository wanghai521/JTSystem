package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台商品信息的业务实现
 *
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClient;

    /**
     * 实现从后台数据管理层，使用http协议进行数据获取
     *
     * @param itemId
     * @return
     */
    @Override
    public Item findItemById(Long itemId) {
        // 连接后端数据管理的url路径
        String url = "http://manage.jt.com/web/item/findItemById";
        Map<String, String> params = new HashMap<>();
        params.put("itemId", itemId + "");// 转换成字符串格式

        // 执行get请求
        String itemJson = httpClient.doGet(url, params);

        return ObjectMapperUtil.jsonToObject(itemJson, Item.class);
    }

    /**
     * 查询商品的详情信息
     *
     * @param itemId
     * @return
     */
    @Override
    public ItemDesc finItemDescById(Long itemId) {
        // 定义查询的url地址
        String url = "http://manage.jt.com/web/item/findItemDescById";
        Map<String, String> params = new HashMap<>();
        params.put("itemId", itemId+"");
        String itemDescJson = httpClient.doGet(url, params);

        return ObjectMapperUtil.jsonToObject(itemDescJson, ItemDesc.class);
    }
}
