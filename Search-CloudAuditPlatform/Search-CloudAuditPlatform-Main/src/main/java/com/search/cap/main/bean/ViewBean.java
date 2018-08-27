package com.search.cap.main.bean;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by heyanjing on 2018/3/23 12:38.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewBean extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String divId;
    private Integer viewType;
    private List<Classify2Bean> classify2List;

    public ViewBean(Integer viewType, List<Classify2Bean> classify2List) {
        this.viewType = viewType;
        this.classify2List = classify2List;
    }

    public ViewBean(Integer viewType) {
        this.viewType = viewType;
    }
}
