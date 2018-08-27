package com.search.cap.main.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/3/21 16:27.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class B extends A {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
