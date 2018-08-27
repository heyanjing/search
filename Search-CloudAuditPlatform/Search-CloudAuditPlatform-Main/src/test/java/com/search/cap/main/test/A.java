package com.search.cap.main.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/3/21 16:26.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class A extends O {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
