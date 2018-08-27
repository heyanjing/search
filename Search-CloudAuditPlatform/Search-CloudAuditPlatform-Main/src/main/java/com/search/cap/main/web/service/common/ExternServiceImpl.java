package com.search.cap.main.web.service.common;

import com.search.cap.main.Capm;
import com.search.wopiserver.api.base.ExternService;
import org.springframework.stereotype.Service;

/**
 * Created by heyanjing on 2018/4/14 12:15.
 */
@Service
public class ExternServiceImpl implements ExternService {
    @Override
    public String getServerNode() {
        return Capm.Upload.SERVER_NAME;
    }

    @Override
    public String getResourceRoot() {
        return Capm.Upload.ROOT;
    }
}
