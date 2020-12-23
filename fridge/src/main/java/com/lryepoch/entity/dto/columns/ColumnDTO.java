package com.lryepoch.entity.dto.columns;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lryepoch
 * @date 2020/10/13 20:25
 * @description TODO 字段对照表实体
 */
@Data
@Accessors(chain = true)
public class ColumnDTO {
    private int id;
    private String column;
    private String columnZh;
    private String type;
    private int flag;
    private int parent;
    private String table;

}
