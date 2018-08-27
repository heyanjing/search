package com.search.cap.main.bean.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfoBean {
    private TableBean tableBean;
    private List<FieldBean> fieldBeanList;

    @Override
    public String toString() {
        return "TableInfoBean{" + "tableBean=" + tableBean + ", fieldBeanList=" + fieldBeanList + '}';
    }

}
