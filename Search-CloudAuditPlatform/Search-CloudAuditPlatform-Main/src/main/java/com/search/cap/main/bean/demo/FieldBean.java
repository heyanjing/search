package com.search.cap.main.bean.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldBean {
    private String fieldName;
    private String fieldComment;

    @Override
    public String toString() {
        return "FieldBean{" + "fieldName='" + fieldName + '\'' + ", fieldComment='" + fieldComment + '\'' + '}';
    }

    @Override
    public boolean equals(Object obj) {
        FieldBean fb = (FieldBean) obj;
        return fieldName.equals(fb.getFieldName());
    }
}
