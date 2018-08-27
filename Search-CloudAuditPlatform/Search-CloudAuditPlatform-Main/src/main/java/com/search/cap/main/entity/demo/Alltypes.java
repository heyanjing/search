package com.search.cap.main.entity.demo;

import com.search.cap.main.base.entity.BaseEntityWithStringId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by heyanjing on 2018/2/10 12:48.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Alltypes extends BaseEntityWithStringId {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer t_int;
    private Long t_long;
    @Column(columnDefinition = "NUMBER(38,2)")
    private Double t_double;
    private LocalDate t_date;
    private LocalDateTime t_datetime;
    private String t_string1;
    @Column(columnDefinition = "NVARCHAR2(20)")
    private String t_string2;
    @Column(columnDefinition = "CLOB")
    private String t_string3;
    @Column(columnDefinition = "NCLOB")
    private String t_string4;

    @NotEmpty(message = "不能为空哟")
    private String userName;
    private String password;
    private String salt;

}
