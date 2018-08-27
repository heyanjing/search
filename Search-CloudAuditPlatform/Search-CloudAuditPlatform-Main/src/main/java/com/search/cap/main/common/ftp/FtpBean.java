package com.search.cap.main.common.ftp;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by heyanjing on 2018/3/23 12:38.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FtpBean extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean success;
    private FTPClient ftp;
}
