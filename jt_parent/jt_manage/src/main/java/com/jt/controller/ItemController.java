package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WangHai
 * @Date 2019/11/4
 * @Describle 商品的控制层
 */

@RestController
@RequestMapping("/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 商品的分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("query")
    public EasyUITable doFindAllItem(Integer page, Integer rows) {

        return itemService.findObject(page, rows);
    }


    /**
     * 根据表单进行插入一个商品信息
     *  2019.11.29加入商品详情信息的插入
     * @return
     */
    @RequestMapping("save")
    public SysResult insrtNewItem(Item item, ItemDesc itemDesc) {

        /*try {
            itemService.insertNewItem(item);
            return SysResult.success(item);
        } catch (Exception e) {
            return SysResult.fail();
        }*/
        System.out.println(itemDesc.toString());
        System.out.println(item.toString());
        // 使用了全局的异常控制，上述可以不写
        itemService.insertNewItem(item,itemDesc);

        return SysResult.success();
    }

    /**
     * 查询商品对应的详情信息
     * 实现数据的回显
     * @param itemId
     * @return
     */
    @RequestMapping("query/item/desc/{itemId}")
    public SysResult findItemDescByItemId(@PathVariable Long itemId){

        ItemDesc itemDesc = itemService.finbItemDescById(itemId);

        return SysResult.success(itemDesc);
    }

    /**
     * 编辑并更新商品信息
     *
     * @return
     */
    @RequestMapping("update")
    public SysResult updataItem(Item item,ItemDesc itemDesc) {
        // 更新数据
        itemService.updataItem(item,itemDesc);
        return SysResult.success();
    }

    /**
     * 下架商品
     *
     * @return
     */
    @RequestMapping("instock")
    public SysResult instockItem(Long[] ids) {
        // 状态码status：1代表上架，2代表下架
        int status = 2;
        itemService.updateStatus(ids, status);
        return SysResult.success();
    }

    /**
     * 上架商品
     *
     * @return
     */
    @RequestMapping("reshelf")
    public SysResult reshelfItem(Long[] ids) {
        // 状态码status：1代表上架，2代表下架
        int status = 1;
        itemService.updateStatus(ids, status);
        return SysResult.success();
    }

    /**
     * 根据一个多个id删除商品信息
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    public SysResult deleteItem(Long[] ids){
        itemService.deleteItem(ids);
        return SysResult.success();
    }

    /**
     * 用于给web层传输数据
     * @return
     */
    @RequestMapping("/{itemId}")
    public String toItems(@PathVariable Long itemId){

        System.out.println("当前商品的ID值："+itemId);
        return "item";
    }
}
