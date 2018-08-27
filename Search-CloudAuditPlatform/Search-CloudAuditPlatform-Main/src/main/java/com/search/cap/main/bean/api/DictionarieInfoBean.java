package com.search.cap.main.bean.api;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/5/19 14:49.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DictionarieInfoBean extends BaseBean {

    /**
     * 资质id
     */
    private String sdictionarieid;
    /**
     * 资质备注
     */
    private String sdesc;

    /**
     * 资质附件
     */
    private AttachInfoBean attach;

}
