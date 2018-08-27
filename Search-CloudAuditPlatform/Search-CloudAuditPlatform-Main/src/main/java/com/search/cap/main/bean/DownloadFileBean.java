package com.search.cap.main.bean;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by heyanjing on 2018/3/23 12:38.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileBean extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String serverName;

    public DownloadFileBean(String fileName) {
        this.fileName = fileName;
    }
}
