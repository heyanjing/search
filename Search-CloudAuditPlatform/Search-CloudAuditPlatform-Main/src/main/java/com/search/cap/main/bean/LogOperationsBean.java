package com.search.cap.main.bean;

import com.search.cap.main.common.enums.ClientTypes;
import com.search.common.base.core.Constants;
import com.search.common.base.core.utils.Guava;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static com.search.common.base.core.Constants.COMMA;
import static com.search.common.base.core.Constants.ENTER;
import static com.search.common.base.web.core.ex.GlobalExceptionHandlerBase.URL_DESC;
import static com.search.common.base.web.core.ex.GlobalExceptionHandlerBase.URL_PARAMS;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOperationsBean {
    private String sId;
    private String sUserId;
    private LocalDateTime ldtCreateTime;
    private String sIp;
    private String sDesc;
    private String sRequestContent;
    private String sResponseContent;
    private Integer iType;

    /**
     * pc端的用户操作日志
     */
    public LogOperationsBean(HttpServletRequest request, String sUserId, String sDesc, String sResponseContent) {
        this.ldtCreateTime = LocalDateTime.now();
        this.sIp = request.getHeader("X-Real-IP");
        this.sId = UUID.randomUUID().toString();
        this.sRequestContent = getRequestContent(request);
        this.iType = ClientTypes.PC.getValue();
        this.sUserId = sUserId;
        this.sDesc = sDesc;
        this.sResponseContent = sResponseContent;
    }

    private String getRequestContent(HttpServletRequest request) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append(ENTER).append(URL_DESC).append(request.getRequestURI()).append(ENTER);
        sb.append(URL_PARAMS).append(ENTER);
        for (Map.Entry<String, String[]> obj : request.getParameterMap().entrySet()) {
            sb.append(obj.getKey()).append(Constants.EQUAL_MARK);
            for (String str : obj.getValue()) {
                sb.append(str).append(COMMA);
            }
            sb.append(ENTER);
        }
        return sb.toString();
    }

}
