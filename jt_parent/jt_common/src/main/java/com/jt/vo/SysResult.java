package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther WangHai
 * @Date 2019/11/11
 * @Describle 是EasyUI框架的
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 进行链式编程
public class SysResult {

    private Integer status; // 状态码，200表示成功（自己规定），不是200就是错误
    private String msg; // 存储信息，正确或者错误
    private Object data; // 存放返回的数据

    /**
     * 无参的
     * @return
     */
    public static SysResult success(){

        return new SysResult(200,null,null);
    }

    // 成功并返回数据
    public static SysResult success(Object data){

        return new SysResult(200,null,data);
    }

    /**
     * 出现错误
     * @return
     */
    public static SysResult fail(){
        return new SysResult(201,"业务执行失败",null);
    }
}
