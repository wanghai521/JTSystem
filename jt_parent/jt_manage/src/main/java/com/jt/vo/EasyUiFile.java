package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * c存储图片的信息
 * @Auther WangHai
 * @Date 2019/11/29
 * @Describle what
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUiFile {

    private Integer error = 0; // 默认为0，表示正确，1表示不正确
    private String url; // 图片储存的地址
    private Integer width; // 图片的宽度，不设置就是默认宽度
    private Integer height;

    // 提供静态的方法，简化之后的使用操作
    public static EasyUiFile fail(){
        // 仅仅传一个第一个值，
        return new EasyUiFile(1,null,null,null);
    }
}
