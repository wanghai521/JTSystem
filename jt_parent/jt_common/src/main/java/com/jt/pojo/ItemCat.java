package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther WangHai
 * @Date 2019/11/5
 * @Describle what
 */

@Data
@Accessors(chain = true) // 链式编程
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private Integer status=1;
    private Integer sortOrder;
    private Boolean isParent;

}
