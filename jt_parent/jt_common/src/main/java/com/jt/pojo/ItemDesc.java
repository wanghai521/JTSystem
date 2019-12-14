package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品详情信息的包装类
 * @Auther WangHai
 * @Date 2019/11/29
 * @Describle what
 */
@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{

    @TableId  // 标识表的主键
    private Long itemId;
    private String itemDesc;

}
