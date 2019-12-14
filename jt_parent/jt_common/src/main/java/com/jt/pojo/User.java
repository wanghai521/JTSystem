package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户的信息pojo
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@TableName("tb_user")
@Data
@Accessors(chain = true)
public class User extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;

}
