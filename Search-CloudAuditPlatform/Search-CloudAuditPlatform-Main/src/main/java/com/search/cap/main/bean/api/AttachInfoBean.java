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
public class AttachInfoBean extends BaseBean {

    /**
     * 附件id
     */
    private String sid;

    /**
     * 附件名称
     */
    private String sname;
    /**
     * 附件路径
     */
    private String spath;

}
