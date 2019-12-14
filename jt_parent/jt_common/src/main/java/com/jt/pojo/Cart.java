package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@Data
@Accessors(chain = true)
@TableName("tb_cart")
public class Cart extends BasePojo {

    @TableId
    private Long id;
    private Long userId;
    private Long itemId;
    private String itemTitle;
    private String itemImage;
    private Long itemPrice;
    private Integer num;
}
