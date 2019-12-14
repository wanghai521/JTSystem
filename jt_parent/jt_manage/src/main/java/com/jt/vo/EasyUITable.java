package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 对easyUI界面table的ajax请求数据的给格式进行适应性的封装
 */
@Data
@AllArgsConstructor
@Accessors(chain = true) // 可以链式
@NoArgsConstructor
public class EasyUITable implements Serializable {

    private static final long serialVersionUID = -557428826743456690L;
    private Integer total;// 表示总数据的行数，用于分页
	private List<?> rows; // 对每行数据的list封装
}
