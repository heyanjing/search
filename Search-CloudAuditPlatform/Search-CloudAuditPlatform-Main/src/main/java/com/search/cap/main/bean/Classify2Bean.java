package com.search.cap.main.bean;

import com.search.cap.main.entity.Functions;
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
public class Classify2Bean extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Functions module;
    private List<Functions> nodes;

}
