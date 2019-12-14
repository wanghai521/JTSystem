package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther WangHai
 * @Date 2019/11/11
 * @Describle 目录树状图的封装对象
 */
@Data
@Accessors(chain = true) // 表示可以链式编程
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {

    private Long id;// 节点的id
    private String text;// 文本信息
    private String state; // 表示列表的状态
}
