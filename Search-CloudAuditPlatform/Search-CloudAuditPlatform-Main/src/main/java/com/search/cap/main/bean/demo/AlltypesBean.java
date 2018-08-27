package com.search.cap.main.bean.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlltypesBean {
    private Integer t_int;
    private Long t_long;
    private Double t_double;
    private LocalDate t_date;
    private LocalDateTime t_datetime;
    private String t_string1;
    private String t_string2;
    private String t_string3;
    private String t_string4;
}
