package com.search.cap.main.bean.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableBean {
    private String tableName;
    private String tableComment;

    @Override
    public String toString() {
        return "TableBean{" + "tableName='" + tableName + '\'' + ", tableComment='" + tableComment + '\'' + '}';
    }
}
