package com.search.cap.main.base.bean;

import com.search.common.base.core.bean.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 基础bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseEntityBean extends BaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected String sId;
    protected String sCreateUserId;
    protected LocalDateTime ldtCreateTime;
    protected String sUpdateUserId;
    protected LocalDateTime ldtUpdateTime;
    protected Integer iState;

}
