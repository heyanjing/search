package com.search.cap.main.bean.demo;

import com.search.cap.main.base.bean.BaseEntityBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by heyanjing on 2017/12/19 9:43.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsersBean extends BaseEntityBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String nickName;
    private Integer age;
    private LocalDate birthday;
    private Date birthday2;
    private LocalDateTime birthday3;
    private Double doublex;
    private Boolean booleanx;


}
